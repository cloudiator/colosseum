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

package models.service;

import com.google.inject.Inject;

import java.util.Optional;

import models.KeyPair;
import models.VirtualMachine;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 28.08.15.
 */
public class DefaultKeyPairModelService extends BaseModelService<KeyPair>
    implements KeyPairModelService {

    @Inject public DefaultKeyPairModelService(ModelRepository<KeyPair> modelRepository) {
        super(modelRepository);
    }

    @Override public Optional<KeyPair> getKeyPair(VirtualMachine virtualMachine) {
        checkNotNull(virtualMachine);

        for (KeyPair keyPair : getAll()) {
            // it is a keypair per virtual machine
            if (keyPair.virtualMachine().isPresent() && keyPair.virtualMachine().get()
                .equals(virtualMachine)) {
                return Optional.of(keyPair);
            }
            // it is a keypair per cloud credential
            if (!keyPair.virtualMachine().isPresent() && virtualMachine.owner().isPresent()
                && keyPair.owner().isPresent() && virtualMachine.owner().get()
                .equals(keyPair.owner().get())) {
                return Optional.of(keyPair);
            }
        }
        return Optional.empty();
    }
}
