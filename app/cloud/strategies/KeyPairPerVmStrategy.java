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

package cloud.strategies;

import cloud.CloudService;
import com.google.inject.Inject;
import de.uniulm.omi.cloudiator.sword.api.extensions.KeyPairService;
import models.KeyPair;
import models.VirtualMachine;
import models.service.KeyPairModelService;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 28.08.15.
 */
public class KeyPairPerVmStrategy extends AbstractKeyPairStrategy {

    private final KeyPairModelService keyPairModelService;

    @Inject public KeyPairPerVmStrategy(KeyPairModelService keyPairModelService,
        CloudService cloudService) {
        super(cloudService);
        this.keyPairModelService = keyPairModelService;
    }

    @Override protected Optional<KeyPair> existsFor(VirtualMachine virtualMachine) {
        return keyPairModelService.getKeyPair(virtualMachine);
    }

    @Override protected KeyPair createKeyPairFor(VirtualMachine virtualMachine,
        KeyPairService keyPairService) {
        de.uniulm.omi.cloudiator.sword.api.domain.KeyPair remoteKeyPair =
            keyPairService.create(virtualMachine.getUuid());

        checkState(remoteKeyPair.privateKey().isPresent(),
            "Expected remote keypair to have a private key, but it has none.");

        KeyPair keyPair =
            new KeyPair(remoteKeyPair.id(), remoteKeyPair.id(), virtualMachine.cloud(),
                virtualMachine.owner().get(), remoteKeyPair.privateKey().get(),
                remoteKeyPair.publicKey(), virtualMachine);
        this.keyPairModelService.save(keyPair);
        return keyPair;
    }


}
