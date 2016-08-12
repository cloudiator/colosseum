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

package cloud.sword;

import de.uniulm.omi.cloudiator.sword.api.domain.KeyPair;
import de.uniulm.omi.cloudiator.sword.api.exceptions.KeyPairException;
import de.uniulm.omi.cloudiator.sword.api.extensions.KeyPairService;

import java.util.List;

/**
 * Created by daniel on 20.05.15.
 */
public class MultiCloudKeyPairService implements KeyPairService {

    public final List<KeyPairService> keyPairServices;

    public MultiCloudKeyPairService(List<KeyPairService> keyPairServices) {
        this.keyPairServices = keyPairServices;
    }

    @Override public KeyPair create(String name) throws KeyPairException {
        KeyPair keyPair = null;
        for (KeyPairService keyPairService : keyPairServices) {
            keyPair = keyPairService.create(name);
        }
        return keyPair;
    }

    @Override public KeyPair create(String name, String publicKey) throws KeyPairException {
        KeyPair keyPair = null;
        for (KeyPairService keyPairService : keyPairServices) {
            keyPair = keyPairService.create(name, publicKey);
        }
        return keyPair;
    }

    @Override public boolean delete(String name) throws KeyPairException {
        boolean success = true;
        for (KeyPairService keyPairService : keyPairServices) {
            boolean result = keyPairService.delete(name);
            if (!result) {
                success = false;
            }
        }
        return success;
    }

    @Override public KeyPair get(String name) throws KeyPairException {
        //TODO: check if want to return null, if it is not present in all key pair services.
        //noinspection LoopStatementThatDoesntLoop
        for (KeyPairService keyPairService : keyPairServices) {
            return keyPairService.get(name);
        }
        return null;
    }
}
