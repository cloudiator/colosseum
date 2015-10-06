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

import models.generic.RemoteResource;

import javax.annotation.Nullable;
import javax.persistence.*;

/**
 * Created by daniel on 18.05.15.
 */


/**
 * @todo somehow validate this constraint, only have one credential per cloud and frontend group (or find a better relational schema)
 */
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cloud_id", "tenant_id"})) @Entity
public class KeyPair extends RemoteResource {

    @ManyToOne(optional = false) private Cloud cloud;
    @ManyToOne(optional = false) private Tenant tenant;

    @Lob private String privateKey;
    @Lob @Nullable @Column(nullable = true) private String publicKey;

    /**
     * No-args constructor for hibernate
     */
    protected KeyPair() {
    }

    public KeyPair(Cloud cloud, Tenant tenant, String privateKey, @Nullable String publicKey,
        @Nullable String remoteId, @Nullable String cloudProviderId) {
        super(remoteId, cloudProviderId);
        this.cloud = cloud;
        this.tenant = tenant;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        this.cloud = cloud;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
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
}
