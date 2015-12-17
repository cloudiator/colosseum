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

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 17.12.15.
 */
public abstract class AbstractKeyPairStrategy implements KeyPairStrategy {

    private final ColosseumComputeService colosseumComputeService;

    @Inject public AbstractKeyPairStrategy(CloudService cloudService) {
        this.colosseumComputeService = cloudService.computeService();
    }

    @Override public final Optional<KeyPair> create(VirtualMachine virtualMachine)
        throws KeyPairException {

        checkNotNull(virtualMachine, "Virtual machine is null.");
        checkArgument(virtualMachine.owner().isPresent(), "Virtual machine has no owner set.");

        if (alreadyExists(virtualMachine).isPresent()) {
            return alreadyExists(virtualMachine);
        }

        //check if we can create a keypair
        com.google.common.base.Optional<KeyPairService> keyPairServiceOptional =
            colosseumComputeService.getKeyPairService(virtualMachine.owner().get());

        if (keyPairServiceOptional.isPresent()) {
            return Optional.of(createKeyPair(virtualMachine, keyPairServiceOptional.get()));
        }
        return Optional.empty();
    }

    protected abstract Optional<KeyPair> alreadyExists(VirtualMachine virtualMachine);

    protected abstract KeyPair createKeyPair(VirtualMachine virtualMachine,
        KeyPairService keyPairService);

}
