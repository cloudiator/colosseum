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

import cloud.resources.KeyPairInCloud;
import de.uniulm.omi.cloudiator.sword.api.exceptions.KeyPairException;
import de.uniulm.omi.cloudiator.sword.api.extensions.KeyPairService;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 10.09.15.
 */
public class DecoratingKeyPairService implements KeyPairService {

    private final KeyPairService delegate;
    private final String cloud;
    private final String credential;

    public DecoratingKeyPairService(KeyPairService delegate, String cloud, String credential) {
        this.cloud = cloud;
        this.credential = credential;

        checkNotNull(delegate);

        this.delegate = delegate;
    }

    @Override public KeyPairInCloud create(String name) throws KeyPairException {
        return new KeyPairInCloud(this.delegate.create(name), cloud, credential);
    }

    @Override public KeyPairInCloud create(String name, String publicKey) throws KeyPairException {
        return new KeyPairInCloud(this.delegate.create(name, publicKey), cloud, credential);
    }

    @Override public boolean delete(String name) throws KeyPairException {
        //todo implement
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Nullable @Override public KeyPairInCloud get(String name) throws KeyPairException {
        //todo implement
        throw new UnsupportedOperationException("Not yet implemented.");
    }
}
