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

import com.google.common.collect.ImmutableList;
import models.generic.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 12.03.15.
 */
@Entity public class Tenant extends Model {

    @ManyToMany private List<FrontendUser> frontendUsers;
    @Column(unique = true, nullable = false) private String name;

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.REMOVE) private List<CloudCredential>
        cloudCredentials;

    /**
     * Empty constructor for hibernate.
     */
    protected Tenant() {

    }

    public Tenant(String name) {
        checkNotNull(name);
        checkArgument(!name.isEmpty());
        this.name = name;
    }

    public List<FrontendUser> getFrontendUsers() {
        return ImmutableList.copyOf(frontendUsers);
    }

    public void addFrontendUser(FrontendUser frontendUser) {
        if (frontendUsers == null) {
            frontendUsers = new ArrayList<>();
        }
        frontendUsers.add(frontendUser);
    }

    public List<CloudCredential> getCloudCredentials() {
        if (cloudCredentials == null) {
            cloudCredentials = Collections.emptyList();
        }
        return ImmutableList.copyOf(cloudCredentials);
    }

    public void setFrontendUsers(List<FrontendUser> frontendUsers) {
        this.frontendUsers = frontendUsers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCloudCredentials(List<CloudCredential> cloudCredentials) {
        this.cloudCredentials = cloudCredentials;
    }
}
