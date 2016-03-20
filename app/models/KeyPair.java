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

package models;

import com.google.common.collect.Lists;
import models.generic.RemoteResourceInCloud;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by daniel on 18.05.15.
 */
@Entity public class KeyPair extends RemoteResourceInCloud {

    @Lob private String privateKey;
    @Lob @Nullable @Column(nullable = true) private String publicKey;
    @Nullable @ManyToOne(optional = true) private VirtualMachine virtualMachine;

    /**
     * No-args constructor for hibernate
     */
    protected KeyPair() {
    }

    public KeyPair(@Nullable String remoteId, @Nullable String providerId, @Nullable String swordId,
        Cloud cloud, @Nullable CloudCredential owner, String privateKey, @Nullable String publicKey,
        @Nullable VirtualMachine virtualMachine) {
        super(remoteId, providerId, swordId, cloud, owner);
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.virtualMachine = virtualMachine;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Nullable public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(@Nullable String publicKey) {
        this.publicKey = publicKey;
    }

    @Override public List<CloudCredential> cloudCredentials() {
        if (owner().isPresent()) {
            return Lists.newArrayList(owner().get());
        }
        return Collections.emptyList();
    }

    /**
     * @todo find a better way to undecorate the keypair for the virtual machine template.
     * Maybe do it in the template itself?
     */
    public String name() {
        return providerId().get();
    }

    public Optional<VirtualMachine> virtualMachine() {
        return Optional.ofNullable(virtualMachine);
    }
}
