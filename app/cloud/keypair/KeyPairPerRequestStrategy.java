/*
 * Copyright (c) 2014-2016 University of Ulm
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

package cloud.keypair;

import cloud.sword.CloudService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.uniulm.omi.cloudiator.sword.api.extensions.KeyPairService;
import models.CloudCredential;
import models.KeyPair;
import models.VirtualMachine;
import models.service.KeyPairModelService;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 28.08.15.
 */
@Singleton public class KeyPairPerRequestStrategy implements KeyPairStrategy {

    private final KeyPairModelService keyPairModelService;
    private final CloudService cloudService;

    @Inject public KeyPairPerRequestStrategy(KeyPairModelService keyPairModelService,
        CloudService cloudService) {

        checkNotNull(keyPairModelService);
        checkNotNull(cloudService);

        this.keyPairModelService = keyPairModelService;
        this.cloudService = cloudService;
    }

    private class KeyPairPerRequestCreationStrategy implements KeyPairCreationStrategy {

        @Override public KeyPair create(CloudCredential owner) throws KeyPairCreationException {

            com.google.common.base.Optional<KeyPairService> keyPairServiceOptional =
                cloudService.computeService().getKeyPairService(owner);

            if (!keyPairServiceOptional.isPresent()) {
                throw new KeyPairCreationException(
                    String.format("KeyPairService is not present for %s", owner));
            }

            try {
                de.uniulm.omi.cloudiator.sword.api.domain.KeyPair remoteKeyPair =
                    keyPairServiceOptional.get().create(null);

                checkState(remoteKeyPair.privateKey().isPresent(),
                    "Remote keyPair does not hold a private key.");

                KeyPair keyPair =
                    new KeyPair(remoteKeyPair.id(), remoteKeyPair.providerId(), remoteKeyPair.id(),
                        owner.cloud(), owner, remoteKeyPair.privateKey().get(),
                        remoteKeyPair.publicKey());
                keyPairModelService.save(keyPair);
                return keyPair;

            } catch (Exception e) {
                throw new KeyPairCreationException(e);
            }
        }
    }


    private class KeyPairPerRequestRetrievalStrategy implements KeyPairRetrievalStrategy {

        @Override public Optional<KeyPair> retrieve(VirtualMachine virtualMachine) {
            return virtualMachine.keyPair();
        }
    }


    @Override public KeyPairCreationStrategy creation() {
        return new KeyPairPerRequestCreationStrategy();
    }

    @Override public KeyPairRetrievalStrategy retrieval() {
        return new KeyPairPerRequestRetrievalStrategy();
    }
}
