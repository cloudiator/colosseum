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

import cloud.resources.HardwareInLocation;
import cloud.resources.ImageInLocation;
import cloud.resources.LocationInCloud;
import cloud.resources.VirtualMachineInLocation;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import de.uniulm.omi.cloudiator.sword.api.domain.*;
import de.uniulm.omi.cloudiator.sword.api.extensions.KeyPairService;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import de.uniulm.omi.cloudiator.sword.api.service.ConnectionService;
import de.uniulm.omi.cloudiator.sword.api.service.DiscoveryService;

import javax.annotation.Nullable;

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


    public DecoratingComputeService(
        ComputeService<HardwareFlavor, Image, Location, VirtualMachine> delegate, String cloudId,
        String cloudCredential) {

        checkNotNull(delegate);
        checkNotNull(cloudId);
        checkArgument(!cloudId.isEmpty());
        checkNotNull(cloudCredential);
        checkArgument(!cloudCredential.isEmpty());

        this.delegate = delegate;
        this.cloudCredential = cloudCredential;
        this.cloudId = cloudId;
    }



    @Override
    public DiscoveryService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> discoveryService() {
        return new DecoratingDiscoveryService(delegate.discoveryService(), cloudId,
            cloudCredential);
    }

    @Override public void deleteVirtualMachine(String s) {
        //TODO: implement this.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override public VirtualMachineInLocation createVirtualMachine(
        VirtualMachineTemplate virtualMachineTemplate) {
        return new VirtualMachineDecorator(cloudId, cloudCredential)
            .apply(this.delegate.createVirtualMachine(virtualMachineTemplate));
    }

    @Override public ConnectionService connectionService() {
        return this.delegate.connectionService();
    }

    @Override public Optional<PublicIpService> publicIpService() {
        return this.delegate.publicIpService();
    }

    @Override public Optional<KeyPairService> keyPairService() {
        return this.delegate.keyPairService();
    }

    static class VirtualMachineDecorator
        implements Function<VirtualMachine, VirtualMachineInLocation> {

        private final String cloudId;
        private final String cloudCredentialId;


        public VirtualMachineDecorator(String cloudId, String cloudCredentialId) {
            this.cloudId = cloudId;
            this.cloudCredentialId = cloudCredentialId;
        }

        @Nullable @Override
        public VirtualMachineInLocation apply(@Nullable VirtualMachine virtualMachine) {
            checkNotNull(virtualMachine);
            return new VirtualMachineInLocation(virtualMachine, cloudId, cloudCredentialId);
        }
    }
}
