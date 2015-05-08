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

import models.generic.Model;

import javax.persistence.*;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 12.03.15.
 */
@Entity public class FrontendGroup extends Model {

    @ManyToMany private List<FrontendUser> frontendUsers;
    @Column(unique = true, nullable = false) private String name;

    @OneToMany(mappedBy = "frontendGroup", cascade = CascadeType.REMOVE)
    private List<CloudCredential> cloudCredentials;

    /**
     * Empty constructor for hibernate.
     */
    private FrontendGroup() {

    }

    public FrontendGroup(String name) {
        checkNotNull(name);
        checkArgument(!name.isEmpty());
        this.name = name;
    }

    public List<FrontendUser> getFrontendUsers() {
        return frontendUsers;
    }

    public List<CloudCredential> getCloudCredentials() {
        return cloudCredentials;
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
