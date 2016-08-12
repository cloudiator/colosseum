/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package models;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import models.generic.Model;
import models.generic.RemoteResourceInCloud;

import javax.persistence.*;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @todo somehow validate this constraint, only have one credential per cloud and frontend group (or find a better relational schema)
 */
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cloud_id", "tenant_id"})) @Entity
public class CloudCredential extends Model {

    @Column(nullable = false) private String user;

    @Lob @Column(nullable = false) private String secret;

    @ManyToOne(optional = false) private Cloud cloud;

    @ManyToOne(optional = false) private Tenant tenant;

    @ManyToMany(mappedBy = "cloudCredentials") private List<RemoteResourceInCloud> remoteResources;

    /**
     * Empty constructor for hibernate.
     */
    protected CloudCredential() {
    }

    public CloudCredential(Cloud cloud, Tenant tenant, String user, String secret) {

        checkNotNull(cloud);
        checkNotNull(tenant);
        checkNotNull(user);
        checkArgument(!user.isEmpty());
        checkNotNull(secret);
        checkArgument(!secret.isEmpty());

        this.cloud = cloud;
        this.tenant = tenant;
        this.user = user;
        this.secret = secret;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Cloud cloud() {
        return cloud;
    }

    public Tenant tenant() {
        return tenant;
    }

    public List<RemoteResourceInCloud> remoteResources() {
        return ImmutableList.copyOf(remoteResources);
    }

    @Override public String toString() {
        return MoreObjects.toStringHelper(this).add("id", getId()).add("cloud", cloud)
            .add("user", user).toString();
    }
}
