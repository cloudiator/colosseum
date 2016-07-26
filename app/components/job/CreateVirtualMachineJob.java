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
import components.installer.ToolPorts;
import components.installer.api.InstallApi;
import de.uniulm.omi.cloudiator.sword.api.domain.LoginCredential;
import de.uniulm.omi.cloudiator.sword.api.exceptions.PublicIpException;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteException;
import de.uniulm.omi.cloudiator.sword.core.domain.TemplateOptionsBuilder;
import models.*;
import models.service.ModelService;
import models.service.PortProvidedService;
import models.service.RemoteModelService;
import play.db.jpa.JPAApi;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 08.05.15.
 */
public class CreateVirtualMachineJob extends AbstractRemoteResourceJob<VirtualMachine> {

    private final KeyPairStrategy keyPairStrategy;
    private final RemoteConnectionStrategy.RemoteConnectionStrategyFactory remoteConnectionFactory;
    private final PortProvidedService portProvidedService;

    public CreateVirtualMachineJob(JPAApi jpaApi, VirtualMachine virtualMachine,
        RemoteModelService<VirtualMachine> modelService, ModelService<Tenant> tenantModelService,
        ColosseumComputeService colosseumComputeService, Tenant tenant,
        KeyPairStrategy keyPairStrategy,
        RemoteConnectionStrategy.RemoteConnectionStrategyFactory remoteConnectionFactory,
        PortProvidedService portProvidedService) {
        super(jpaApi, virtualMachine, modelService, tenantModelService, colosseumComputeService,
            tenant);

        checkNotNull(keyPairStrategy);
        checkNotNull(remoteConnectionFactory);

        this.keyPairStrategy = keyPairStrategy;
        this.remoteConnectionFactory = remoteConnectionFactory;
        this.portProvidedService = portProvidedService;
    }

    @Override public boolean canStart() {
        return true;
    }

    @Override protected void doWork(ModelService<VirtualMachine> modelService,
        ColosseumComputeService computeService) throws JobException {

        java.util.Optional<KeyPair> keyPairOptional;
        synchronized (CreateVirtualMachineJob.class) {
            try {
                keyPairOptional = jpaApi().withTransaction(() -> {
                    VirtualMachine virtualMachine = getT();
                    return keyPairStrategy.create(virtualMachine);
                });
            } catch (Throwable throwable) {
                throw new JobException(throwable);
            }
        }

        VirtualMachineInLocation cloudVirtualMachine;
        try {
            cloudVirtualMachine = jpaApi().withTransaction("default", true, () -> {
                VirtualMachine virtualMachine = getT();
                // build the template
                ColosseumVirtualMachineTemplateBuilder builder =
                    BaseColosseumVirtualMachineTemplate.builder();
                TemplateOptionsBuilder templateOptionsBuilder = TemplateOptionsBuilder.newBuilder();
                if (keyPairOptional.isPresent()) {
                    templateOptionsBuilder.keyPairName(keyPairOptional.get().name());
                }
                templateOptionsBuilder.inboundPorts(ToolPorts.inBoundPorts());
                templateOptionsBuilder.inboundPorts(portProvidedService.allProvidedPorts());
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


        jpaApi().withTransaction(() -> {
            VirtualMachine virtualMachine = getT();
            // set values to the model
            virtualMachine.bindRemoteId(cloudVirtualMachine.id());
            virtualMachine
                .bindProviderIds(cloudVirtualMachine.swordId(), cloudVirtualMachine.providerId());
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
            jpaApi().withTransaction("default", true, () -> {
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
}
