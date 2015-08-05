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
import com.google.common.base.Optional;
import components.installer.Installers;
import de.uniulm.omi.cloudiator.sword.api.exceptions.KeyPairException;
import de.uniulm.omi.cloudiator.sword.api.exceptions.PublicIpException;
import de.uniulm.omi.cloudiator.sword.api.extensions.KeyPairService;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;
import de.uniulm.omi.cloudiator.sword.core.domain.builders.TemplateOptionsBuilder;
import models.*;
import models.service.ModelService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by daniel on 08.05.15.
 */
public class CreateVirtualMachineJob extends GenericJob<VirtualMachine> {

    private final ModelService<KeyPair> keyPairModelService;

    public CreateVirtualMachineJob(VirtualMachine virtualMachine,
        ModelService<VirtualMachine> modelService, ModelService<Tenant> tenantModelService,
        ColosseumComputeService colosseumComputeService, Tenant tenant,
        ModelService<KeyPair> keyPairModelService) {
        super(virtualMachine, modelService, tenantModelService, colosseumComputeService, tenant);
        this.keyPairModelService = keyPairModelService;
    }

    @Override
    protected void doWork(VirtualMachine virtualMachine, ModelService<VirtualMachine> modelService,
        ColosseumComputeService computeService, Tenant tenant) throws JobException {

        //check keypair
        KeyPair keyPairToUse = null;
        if (virtualMachine.supportsKeyPair()) {
            for (KeyPair keyPair : keyPairModelService.getAll()) {
                if (keyPair.getCloud().equals(virtualMachine.cloud()) && keyPair.getTenant()
                    .equals(tenant)) {
                    keyPairToUse = keyPair;
                    break;
                }
            }

            if (keyPairToUse == null) {
                Optional<KeyPairService> keyPairServiceOptional = computeService
                    .getKeyPairService(virtualMachine.getCloudCredentials().get(0).getUuid());
                if (keyPairServiceOptional.isPresent()) {
                    try {
                        final de.uniulm.omi.cloudiator.sword.api.domain.KeyPair remoteKeyPair =
                            keyPairServiceOptional.get().create(tenant.getUuid());
                        keyPairToUse = new KeyPair(virtualMachine.cloud(), tenant,
                            remoteKeyPair.privateKey().get(), remoteKeyPair.publicKey(),
                            remoteKeyPair.name());
                        this.keyPairModelService.save(keyPairToUse);
                    } catch (KeyPairException e) {
                        throw new JobException(e);
                    }
                }
            }
        }

        ColosseumVirtualMachineTemplateBuilder builder =
            BaseColosseumVirtualMachineTemplate.builder();

        TemplateOptionsBuilder templateOptionsBuilder = TemplateOptionsBuilder.newBuilder();

        if (keyPairToUse != null) {
            templateOptionsBuilder.keyPairName(keyPairToUse.getRemoteId());
        }

        //todo add inbound ports
        templateOptionsBuilder.inboundPorts(RequiredPorts.inBoundPorts());


        builder.templateOptions(templateOptionsBuilder.build());
        VirtualMachineInLocation cloudVirtualMachine = computeService
            .createVirtualMachine(builder.virtualMachineModel(virtualMachine).build());
        virtualMachine.setRemoteId(cloudVirtualMachine.id());
        for (String ip : cloudVirtualMachine.privateAddresses()) {
            virtualMachine.addIpAddress(new IpAddress(virtualMachine, ip, IpType.PRIVATE));
        }
        for (String ip : cloudVirtualMachine.publicAddresses()) {
            virtualMachine.addIpAddress(new IpAddress(virtualMachine, ip, IpType.PUBLIC));
        }
        modelService.save(virtualMachine);

        if (virtualMachine.publicIpAddress() == null) {
            final Optional<PublicIpService> publicIpService = computeService
                .getPublicIpService(virtualMachine.getCloudCredentials().get(0).getUuid());
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
    }

    private static class RequiredPorts {

        static String ports = "22,1099,4242,8080,9001,9002,5985,443,445";

        private static Set<Integer> inBoundPorts() {
            Set<Integer> intPorts = new HashSet<>();
            for (String port : ports.split(",")) {
                intPorts.add(Integer.valueOf(port));
            }
            return intPorts;
        }

    }
}
