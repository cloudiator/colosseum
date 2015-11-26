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
import models.KeyPair;
import models.VirtualMachine;
import models.service.KeyPairModelService;

import java.util.Optional;

import static com.google.common.base.Preconditions.*;

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

    @Override public Optional<KeyPair> retrieve(VirtualMachine virtualMachine)
        throws KeyPairException {

        checkNotNull(virtualMachine, "Virtual machine is null.");
        checkArgument(virtualMachine.owner().isPresent(), "Virtual machine has no owner set.");

        Optional<KeyPair> exists = keyPairModelService.getKeyPair(virtualMachine);

        if (exists.isPresent()) {
            return exists;
        }

        //check if we can create a keypair
        com.google.common.base.Optional<KeyPairService> keyPairServiceOptional =
            colosseumComputeService.getKeyPairService(virtualMachine.owner().get());

        if (keyPairServiceOptional.isPresent()) {
            de.uniulm.omi.cloudiator.sword.api.domain.KeyPair remoteKeyPair =
                keyPairServiceOptional.get().get(virtualMachine.getUuid());
            if (remoteKeyPair != null) {
                //remote key pair does exists but its not in the database
                throw new IllegalStateException(
                    String.format("Keypair %s does already exists remotely.", remoteKeyPair));
            }
            remoteKeyPair = keyPairServiceOptional.get().create(virtualMachine.getUuid());

            checkState(remoteKeyPair.privateKey().isPresent(),
                "Expected remote keypair to have a private key, but it has none.");

            KeyPair keyPair =
                new KeyPair(remoteKeyPair.id(), remoteKeyPair.id(), virtualMachine.cloud(),
                    virtualMachine.owner().get(), remoteKeyPair.privateKey().get(),
                    remoteKeyPair.publicKey(), virtualMachine);
            this.keyPairModelService.save(keyPair);
            return Optional.of(keyPair);
        }

        return Optional.empty();

    }
}
