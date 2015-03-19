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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"frontendGroup_id, cloud_id"}))
@Entity public class CloudCredential extends Model {

    @Column(nullable = false) private String user;

    @Column(nullable = false) private String secret;

    @ManyToOne(optional = false) private Cloud cloud;

    @ManyToOne(optional = false) private FrontendGroup frontendGroup;

    @ManyToMany(mappedBy = "cloudCredentials") private List<Image> images;

    @ManyToMany(mappedBy = "cloudCredentials") private List<Hardware> hardware;

    @ManyToMany(mappedBy = "cloudCredentials") private List<Location> locations;

    /**
     * Empty constructor for hibernate.
     */
    private CloudCredential() {
    }

    public CloudCredential(Cloud cloud, FrontendGroup frontendGroup, String user, String secret) {

        checkNotNull(cloud);
        checkNotNull(frontendGroup);
        checkNotNull(user);
        checkArgument(!user.isEmpty());
        checkNotNull(secret);
        checkArgument(!secret.isEmpty());

        this.cloud = cloud;
        this.frontendGroup = frontendGroup;
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

    public Cloud getCloud() {
        return cloud;
    }

    public FrontendGroup getFrontendGroup() {
        return frontendGroup;
    }

    public List<Image> getImages() {
        return images;
    }

    public List<Hardware> getHardware() {
        return hardware;
    }

    public List<Location> getLocations() {
        return locations;
    }
}
