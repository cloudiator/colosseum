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

package cloud;

import com.google.common.base.Optional;

import de.uniulm.omi.cloudiator.sword.api.domain.HardwareFlavor;
import de.uniulm.omi.cloudiator.sword.api.domain.Image;
import de.uniulm.omi.cloudiator.sword.api.domain.Location;
import de.uniulm.omi.cloudiator.sword.api.domain.VirtualMachine;
import de.uniulm.omi.cloudiator.sword.api.domain.VirtualMachineTemplate;
import de.uniulm.omi.cloudiator.sword.api.extensions.KeyPairService;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import de.uniulm.omi.cloudiator.sword.api.service.ConnectionService;
import de.uniulm.omi.cloudiator.sword.api.service.DiscoveryService;

import java.util.function.Function;

import cloud.resources.HardwareInLocation;
import cloud.resources.ImageInLocation;
import cloud.resources.LocationInCloud;
import cloud.resources.VirtualMachineInLocation;
import models.Cloud;
import models.CloudCredential;
import models.service.ModelService;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 28.05.15.
 */
public class DecoratingComputeService implements
    ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> {

    private final ComputeService<HardwareFlavor, Image, Location, VirtualMachine> delegate;
    private final String cloudId;
    private final String cloudCredential;
    private final ModelService<CloudCredential> cloudCredentialModelService;
    private final ModelService<Cloud> cloudModelService;


    public DecoratingComputeService(
        ComputeService<HardwareFlavor, Image, Location, VirtualMachine> delegate, String cloudId,
        String cloudCredential, ModelService<Cloud> cloudModelService,
        ModelService<CloudCredential> cloudCredentialModelService) {

        checkNotNull(delegate);
        checkNotNull(cloudId);
        checkArgument(!cloudId.isEmpty());
        checkNotNull(cloudCredential);
        checkArgument(!cloudCredential.isEmpty());
        checkNotNull(cloudModelService);
        checkNotNull(cloudCredentialModelService);

        this.delegate = delegate;
        this.cloudCredential = cloudCredential;
        this.cloudId = cloudId;
        this.cloudModelService = cloudModelService;
        this.cloudCredentialModelService = cloudCredentialModelService;
    }

    @Override
    public DiscoveryService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> discoveryService() {
        return new DecoratingDiscoveryService(delegate.discoveryService(), cloudId, cloudCredential,
            cloudModelService, cloudCredentialModelService);
    }

    @Override public void deleteVirtualMachine(String s) {
        this.delegate.deleteVirtualMachine(s);
    }

    @Override public VirtualMachineInLocation createVirtualMachine(
        VirtualMachineTemplate virtualMachineTemplate) {
        return new VirtualMachineDecorator(cloudId, cloudCredential, cloudCredentialModelService,
            cloudModelService).apply(this.delegate.createVirtualMachine(virtualMachineTemplate));
    }

    @Override public ConnectionService connectionService() {
        return this.delegate.connectionService();
    }

    @Override public Optional<PublicIpService> publicIpService() {
        Optional<PublicIpService> publicIpService = delegate.publicIpService();
        if (!publicIpService.isPresent()) {
            return Optional.absent();
        }
        return Optional.of(new DecoratingPublicIpService(publicIpService.get()));
    }

    @Override public Optional<KeyPairService> keyPairService() {
        if (!delegate.keyPairService().isPresent()) {
            return Optional.absent();
        }
        return Optional.of(new DecoratingKeyPairService(delegate.keyPairService().get(), cloudId,
            cloudCredential, cloudModelService, cloudCredentialModelService));
    }

    static class VirtualMachineDecorator
        implements Function<VirtualMachine, VirtualMachineInLocation> {

        private final String cloudId;
        private final String cloudCredentialId;
        private final ModelService<CloudCredential> cloudCredentialModelService;
        private final ModelService<Cloud> cloudModelService;


        public VirtualMachineDecorator(String cloudId, String cloudCredentialId,
            ModelService<CloudCredential> cloudCredentialModelService,
            ModelService<Cloud> cloudModelService) {

            this.cloudId = cloudId;
            this.cloudCredentialId = cloudCredentialId;
            this.cloudCredentialModelService = cloudCredentialModelService;
            this.cloudModelService = cloudModelService;
        }

        @Override public VirtualMachineInLocation apply(VirtualMachine virtualMachine) {
            return new VirtualMachineInLocation(virtualMachine, cloudId, cloudCredentialId,
                cloudModelService, cloudCredentialModelService);
        }
    }
}
