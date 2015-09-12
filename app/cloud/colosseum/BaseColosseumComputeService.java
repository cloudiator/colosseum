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

package cloud.colosseum;

import cloud.ComputeServiceRegistry;
import cloud.DecoratingKeyPairService;
import cloud.DecoratingPublicIpService;
import cloud.resources.VirtualMachineInLocation;
import cloud.strategies.RemoteConnectionStrategy;
import com.google.common.base.Optional;
import de.uniulm.omi.cloudiator.sword.api.extensions.KeyPairService;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;
import models.CloudCredential;
import models.Tenant;
import models.VirtualMachine;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 21.06.15.
 */
public class BaseColosseumComputeService implements ColosseumComputeService {

    private final ComputeServiceRegistry computeServices;
    private final RemoteConnectionStrategy.RemoteConnectionStrategyFactory
        remoteConnectionStrategyFactory;


    public BaseColosseumComputeService(ComputeServiceRegistry computeServices,
        RemoteConnectionStrategy.RemoteConnectionStrategyFactory remoteConnectionStrategyFactory) {

        checkNotNull(computeServices);
        checkNotNull(remoteConnectionStrategyFactory);

        this.computeServices = computeServices;
        this.remoteConnectionStrategyFactory = remoteConnectionStrategyFactory;
    }

    @Override public VirtualMachineInLocation createVirtualMachine(
        ColosseumVirtualMachineTemplate virtualMachineTemplate) {
        return this.computeServices.getComputeService(virtualMachineTemplate.cloudCredentialUuid())
            .createVirtualMachine(virtualMachineTemplate);
    }

    @Override
    public RemoteConnection remoteConnection(Tenant tenant, VirtualMachine virtualMachine) {
        checkNotNull(tenant);
        checkNotNull(virtualMachine);

        RemoteConnectionStrategy remoteConnectionStrategy =
            remoteConnectionStrategyFactory.create(tenant);

        if (!remoteConnectionStrategy.isApplicable(virtualMachine)) {
            throw new IllegalArgumentException(String
                .format("Selected connection strategy does not support the virtual machine %s",
                    virtualMachine));
        }
        return remoteConnectionStrategy.apply(virtualMachine);
    }

    @Override public Optional<KeyPairService> getKeyPairService(CloudCredential cloudCredential) {
        final Optional<KeyPairService> keyPairService =
            this.computeServices.getComputeService(cloudCredential.getUuid()).getKeyPairService();
        if (!keyPairService.isPresent()) {
            return Optional.absent();
        }
        return Optional.of(new DecoratingKeyPairService(keyPairService.get(),
            cloudCredential.getCloud().getUuid(), cloudCredential.getUuid()));
    }

    @Override public Optional<PublicIpService> getPublicIpService(String cloudCredentialUuid) {
        Optional<PublicIpService> publicIpService =
            computeServices.getComputeService(cloudCredentialUuid).getPublicIpService();
        if (!publicIpService.isPresent()) {
            return Optional.absent();
        }
        return Optional.of(new DecoratingPublicIpService(publicIpService.get()));
    }
}
