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
import com.google.common.base.Optional;
import components.installer.Installers;
import de.uniulm.omi.cloudiator.sword.api.exceptions.KeyPairException;
import de.uniulm.omi.cloudiator.sword.api.exceptions.PublicIpException;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;
import de.uniulm.omi.cloudiator.sword.core.domain.TemplateOptionsBuilder;
import models.*;
import models.generic.RemoteState;
import models.service.ModelService;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 08.05.15.
 */
public class CreateVirtualMachineJob extends GenericJob<VirtualMachine> {

    private final KeyPairStrategy keyPairStrategy;

    public CreateVirtualMachineJob(VirtualMachine virtualMachine,
        ModelService<VirtualMachine> modelService, ModelService<Tenant> tenantModelService,
        ColosseumComputeService colosseumComputeService, Tenant tenant,
        KeyPairStrategy keyPairStrategy) {
        super(virtualMachine, modelService, tenantModelService, colosseumComputeService, tenant);

        checkNotNull(keyPairStrategy);

        this.keyPairStrategy = keyPairStrategy;
    }

    @Override
    protected void doWork(VirtualMachine virtualMachine, ModelService<VirtualMachine> modelService,
        ColosseumComputeService computeService, Tenant tenant) throws JobException {

        final java.util.Optional<KeyPair> keyPairOptional;
        if (virtualMachine.supportsKeyPair()) {
            try {
                keyPairOptional = keyPairStrategy.retrieve(virtualMachine.cloud(), tenant);
            } catch (KeyPairException e) {
                throw new JobException(e);
            }
        } else {
            keyPairOptional = java.util.Optional.empty();
        }


        // build the template
        ColosseumVirtualMachineTemplateBuilder builder =
            BaseColosseumVirtualMachineTemplate.builder();
        TemplateOptionsBuilder templateOptionsBuilder = TemplateOptionsBuilder.newBuilder();
        if (keyPairOptional.isPresent()) {
            templateOptionsBuilder.keyPairName(keyPairOptional.get().getCloudProviderId());
        }
        templateOptionsBuilder.inboundPorts(RequiredPorts.inBoundPorts());
        builder.templateOptions(templateOptionsBuilder.build());

        // create the virtual machine
        VirtualMachineInLocation cloudVirtualMachine = computeService
            .createVirtualMachine(builder.virtualMachineModel(virtualMachine).build());

        // set values to the model
        virtualMachine.setRemoteId(cloudVirtualMachine.id());
        virtualMachine.setCloudProviderId(cloudVirtualMachine.cloudProviderId());
        for (String ip : cloudVirtualMachine.privateAddresses()) {
            virtualMachine.addIpAddress(new IpAddress(virtualMachine, ip, IpType.PRIVATE));
        }
        for (String ip : cloudVirtualMachine.publicAddresses()) {
            virtualMachine.addIpAddress(new IpAddress(virtualMachine, ip, IpType.PUBLIC));
        }

        //todo we cannot trust the response of sword, as jclouds returns wrong usernames.
        //fix this in sword. until fixed we do not read the login credentials.
        //this will cause flexiant jobs to fail....
        //if (cloudVirtualMachine.loginCredential().isPresent()) {
        //    LoginCredential loginCredential = cloudVirtualMachine.loginCredential().get();
        //    virtualMachine.setGeneratedLoginUsername(loginCredential.username());
        //    if (loginCredential.isPasswordCredential()) {
        //        virtualMachine.setGeneratedPassword(loginCredential.password().get());
        //    } else {
        //        //todo: if a private key and a public key are returned, we need to store them
        //        throw new UnsupportedOperationException(
        //            "Virtual Machine that started with key credentials is not yet supported.");
        //    }
        //}

        modelService.save(virtualMachine);

        if (!virtualMachine.publicIpAddress().isPresent()) {
            final Optional<PublicIpService> publicIpService = computeService
                .getPublicIpService(virtualMachine.cloudCredentials().get(0).getUuid());
            if (publicIpService.isPresent()) {
                try {
                    final String publicIp =
                        publicIpService.get().addPublicIp(virtualMachine.getRemoteId());
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

        final RemoteConnection remoteConnection =
            computeService.remoteConnection(tenant, virtualMachine);

        Installers.of(remoteConnection, virtualMachine, tenant).installAll();

        virtualMachine.setRemoteState(RemoteState.OK);
        modelService.save(virtualMachine);
    }

    @Override public boolean canStart() {
        return true;
    }

    /**
     * @todo installers need to be able to register those ports
     */
    private static class RequiredPorts {

        static String ports = "22,80,1099,3306,4242,8080,9001,9002,5985,443,445,33033";

        private static Set<Integer> inBoundPorts() {
            Set<Integer> intPorts = new HashSet<>();
            for (String port : ports.split(",")) {
                intPorts.add(Integer.valueOf(port));
            }
            return intPorts;
        }

    }
}
