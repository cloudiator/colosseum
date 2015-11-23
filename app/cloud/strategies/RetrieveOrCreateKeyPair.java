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
import cloud.colosseum.ColosseumComputeService;
import com.google.inject.Inject;
import de.uniulm.omi.cloudiator.sword.api.exceptions.KeyPairException;
import de.uniulm.omi.cloudiator.sword.api.extensions.KeyPairService;
import models.CloudCredential;
import models.KeyPair;
import models.service.KeyPairModelService;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 28.08.15.
 */
public class RetrieveOrCreateKeyPair implements KeyPairStrategy {

    private final KeyPairModelService keyPairModelService;
    private final ColosseumComputeService colosseumComputeService;

    @Inject public RetrieveOrCreateKeyPair(KeyPairModelService keyPairModelService,
        CloudService cloudService) {
        this.keyPairModelService = keyPairModelService;
        this.colosseumComputeService = cloudService.computeService();
    }

    @Override public Optional<KeyPair> retrieve(CloudCredential cloudCredential)
        throws KeyPairException {

        checkNotNull(cloudCredential);

        Optional<KeyPair> exists = keyPairModelService.getKeyPair(cloudCredential);

        if (exists.isPresent()) {
            return exists;
        }

        //check if we can create a keypair
        com.google.common.base.Optional<KeyPairService> keyPairServiceOptional =
            colosseumComputeService.getKeyPairService(cloudCredential);

        if (keyPairServiceOptional.isPresent()) {
            final de.uniulm.omi.cloudiator.sword.api.domain.KeyPair remoteKeyPair =
                keyPairServiceOptional.get().create(cloudCredential.getUuid());

            checkState(remoteKeyPair.privateKey().isPresent(),
                "Expected remote keypair to have a private key, but it has none.");

            KeyPair keyPair =
                new KeyPair(remoteKeyPair.id(), remoteKeyPair.id(), cloudCredential.getCloud(),
                    cloudCredential, remoteKeyPair.privateKey().get(), remoteKeyPair.publicKey());
            this.keyPairModelService.save(keyPair);
            return Optional.of(keyPair);
        }

        return Optional.empty();

    }
}
