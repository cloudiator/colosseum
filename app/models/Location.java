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

@Entity public class Location extends Model {

    private String cloudUuid;

    @ManyToOne(optional = false) private LocationOffer locationOffer;

    @ManyToOne(optional = false) private Cloud cloud;

    @ManyToMany private List<CloudCredential> cloudCredentials;

    @OneToMany(mappedBy = "location", cascade = CascadeType.REMOVE)
    private List<VirtualMachineTemplate> virtualMachineTemplates;

    @ManyToMany(mappedBy = "locations", cascade = CascadeType.REMOVE) private List<Image> images;

    @ManyToMany(mappedBy = "locations", cascade = CascadeType.REMOVE) private List<Hardware>
        hardwareList;

    /**
     * Empty constructor for hibernate.
     */
    private Location() {
    }

    public Location(String cloudUuid, Cloud cloud, LocationOffer locationOffer) {
        this.cloudUuid = cloudUuid;
        this.cloud = cloud;
        this.locationOffer = locationOffer;
    }

    public String getCloudUuid() {
        return cloudUuid;
    }

    public void setCloudUuid(String cloudUuid) {
        this.cloudUuid = cloudUuid;
    }

    public LocationOffer getLocationOffer() {
        return locationOffer;
    }

    public void setLocationOffer(LocationOffer locationOffer) {
        this.locationOffer = locationOffer;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        this.cloud = cloud;
    }

    public List<CloudCredential> getCloudCredentials() {
        return cloudCredentials;
    }

    public void setCloudCredentials(List<CloudCredential> cloudCredentials) {
        this.cloudCredentials = cloudCredentials;
    }

    public List<VirtualMachineTemplate> getVirtualMachineTemplates() {
        return virtualMachineTemplates;
    }

    public void setVirtualMachineTemplates(List<VirtualMachineTemplate> virtualMachineTemplates) {
        this.virtualMachineTemplates = virtualMachineTemplates;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Hardware> getHardwareList() {
        return hardwareList;
    }

    public void setHardwareList(List<Hardware> hardwareList) {
        this.hardwareList = hardwareList;
    }
}
