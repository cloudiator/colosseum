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
 * @todo somehow validate this constraint, only have one credential per cloud and frontend group (or find a better relational schema)
 */
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cloud_id", "frontendGroup_id"}))
@Entity public class CloudCredential extends Model {

    @Column(nullable = false) private String user;

    @Column(nullable = false) private String secret;

    @ManyToOne(optional = false) private Cloud cloud;

    @ManyToOne(optional = false) private FrontendGroup frontendGroup;

    @ManyToMany(mappedBy = "cloudCredentials") private List<Image> images;

    @ManyToMany(mappedBy = "cloudCredentials") private List<Hardware> hardware;

    @ManyToMany(mappedBy = "cloudCredentials") private List<Location> locations;

    @ManyToMany(mappedBy = "cloudCredentials") private List<VirtualMachine> virtualMachines;

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

    public void setCloud(Cloud cloud) {
        this.cloud = cloud;
    }

    public FrontendGroup getFrontendGroup() {
        return frontendGroup;
    }

    public void setFrontendGroup(FrontendGroup frontendGroup) {
        this.frontendGroup = frontendGroup;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Hardware> getHardware() {
        return hardware;
    }

    public void setHardware(List<Hardware> hardware) {
        this.hardware = hardware;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<VirtualMachine> getVirtualMachines() {
        return virtualMachines;
    }

    public void setVirtualMachines(List<VirtualMachine> virtualMachines) {
        this.virtualMachines = virtualMachines;
    }
}
