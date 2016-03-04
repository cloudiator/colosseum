/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package components.job;

import cloud.colosseum.BaseColosseumVirtualMachineTemplate;
import cloud.colosseum.ColosseumComputeService;
import cloud.colosseum.ColosseumVirtualMachineTemplateBuilder;
import cloud.resources.VirtualMachineInLocation;
import cloud.strategies.KeyPairStrategy;
import cloud.strategies.RemoteConnectionStrategy;
import com.google.common.base.Optional;
import components.installer.Installers;
import components.installer.api.InstallApi;
import de.uniulm.omi.cloudiator.sword.api.domain.LoginCredential;
import de.uniulm.omi.cloudiator.sword.api.exceptions.PublicIpException;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteException;
import de.uniulm.omi.cloudiator.sword.core.domain.TemplateOptionsBuilder;
import models.*;
import models.service.ModelService;
import models.service.RemoteModelService;
import play.db.jpa.JPA;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 08.05.15.
 */
public class CreateVirtualMachineJob extends AbstractRemoteResourceJob<VirtualMachine> {

    private final KeyPairStrategy keyPairStrategy;
    private final RemoteConnectionStrategy.RemoteConnectionStrategyFactory remoteConnectionFactory;

    public CreateVirtualMachineJob(VirtualMachine virtualMachine,
        RemoteModelService<VirtualMachine> modelService, ModelService<Tenant> tenantModelService,
        ColosseumComputeService colosseumComputeService, Tenant tenant,
        KeyPairStrategy keyPairStrategy,
        RemoteConnectionStrategy.RemoteConnectionStrategyFactory remoteConnectionFactory) {
        super(virtualMachine, modelService, tenantModelService, colosseumComputeService, tenant);

        checkNotNull(keyPairStrategy);
        checkNotNull(remoteConnectionFactory);

        this.keyPairStrategy = keyPairStrategy;
        this.remoteConnectionFactory = remoteConnectionFactory;
    }

    @Override public boolean canStart() {
        return true;
    }

    @Override protected void doWork(ModelService<VirtualMachine> modelService,
        ColosseumComputeService computeService) throws JobException {

        java.util.Optional<KeyPair> keyPairOptional;
        synchronized (CreateVirtualMachineJob.class) {
            try {
                keyPairOptional = JPA.withTransaction(() -> {
                    VirtualMachine virtualMachine = getT();
                    return keyPairStrategy.create(virtualMachine);
                });
            } catch (Throwable throwable) {
                throw new JobException(throwable);
            }
        }

        VirtualMachineInLocation cloudVirtualMachine;
        try {
            cloudVirtualMachine = JPA.withTransaction("default", true, () -> {
                VirtualMachine virtualMachine = getT();
                // build the template
                ColosseumVirtualMachineTemplateBuilder builder =
                    BaseColosseumVirtualMachineTemplate.builder();
                TemplateOptionsBuilder templateOptionsBuilder = TemplateOptionsBuilder.newBuilder();
                if (keyPairOptional.isPresent()) {
                    templateOptionsBuilder.keyPairName(keyPairOptional.get().name());
                }
                templateOptionsBuilder.inboundPorts(RequiredPorts.inBoundPorts());
                if (virtualMachine.templateOptions().isPresent()) {
                    templateOptionsBuilder.tags(virtualMachine.templateOptions().get().tags());
                }
                builder.templateOptions(templateOptionsBuilder.build());

                // create the virtual machine (we use synchronized to avoid problems
                // with multiple created security groups)
                synchronized (CreateVirtualMachineJob.class) {
                    return computeService
                        .createVirtualMachine(builder.virtualMachineModel(virtualMachine).build());
                }
            });
        } catch (Throwable throwable) {
            throw new JobException(throwable);
        }


        JPA.withTransaction(() -> {
            VirtualMachine virtualMachine = getT();
            // set values to the model
            virtualMachine.bindRemoteId(cloudVirtualMachine.id());
            virtualMachine.bindCloudProviderId(cloudVirtualMachine.cloudProviderId());
            for (String ip : cloudVirtualMachine.privateAddresses()) {
                virtualMachine.addIpAddress(new IpAddress(virtualMachine, ip, IpType.PRIVATE));
            }
            for (String ip : cloudVirtualMachine.publicAddresses()) {
                virtualMachine.addIpAddress(new IpAddress(virtualMachine, ip, IpType.PUBLIC));
            }

            //todo we cannot trust the response of sword, as jclouds returns wrong usernames.
            //fix this in sword. until fixed we do not read the login credentials.
            //this will cause flexiant jobs to fail....
            if (cloudVirtualMachine.loginCredential().isPresent()) {
                LoginCredential loginCredential = cloudVirtualMachine.loginCredential().get();
                virtualMachine.setGeneratedLoginUsername(loginCredential.username().orElse(null));
                virtualMachine.setGeneratedLoginPassword(loginCredential.password().orElse(null));
                virtualMachine.setGeneratedPrivateKey(loginCredential.privateKey().orElse(null));
            }

            modelService.save(virtualMachine);

            if (!virtualMachine.publicIpAddress().isPresent()) {
                final Optional<PublicIpService> publicIpService =
                    computeService.getPublicIpService(virtualMachine.owner().get());
                if (publicIpService.isPresent()) {
                    try {
                        final String publicIp =
                            publicIpService.get().addPublicIp(virtualMachine.remoteId().get());
                        virtualMachine
                            .addIpAddress(new IpAddress(virtualMachine, publicIp, IpType.PUBLIC));
                    } catch (PublicIpException e) {
                        throw new JobException(e);
                    }
                } else {
                    throw new JobException(
                        "VirtualMachine started without public IP and IpService is not available.");
                }
            }
        });

        try {
            JPA.withTransaction("default", true, () -> {
                VirtualMachine virtualMachine = getT();
                Tenant tenant = getTenant();
                final RemoteConnection remoteConnection =
                    remoteConnectionFactory.create().connect(virtualMachine);

                try (InstallApi installApi = Installers
                    .of(remoteConnection, virtualMachine, tenant)) {
                    installApi.installAll();
                } catch (RemoteException e) {
                    throw new JobException(e);
                }
                return null;
            });
        } catch (Throwable throwable) {
            throw new JobException(throwable);
        }
    }

    /**
     * @todo installers need to be able to register those ports
     */
    private static class RequiredPorts {

        static String ports =
            "8083,7072,7071,7070,2510,8082,9092,9042,2181,22,80,1099,3306,4242,8080,8081,9001,9002,5985,443,445,33033,30001,10001";

        private static Set<Integer> inBoundPorts() {
            Set<Integer> intPorts = new HashSet<>();
            for (String port : ports.split(",")) {
                intPorts.add(Integer.valueOf(port));
            }
            return intPorts;
        }

    }
}
