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

@Entity public class Hardware extends Model {

    @Column(updatable = false) private String cloudUuid;

    @ManyToOne(optional = false) private HardwareOffer hardwareOffer;

    @ManyToOne(optional = false) private Cloud cloud;

    @ManyToMany private List<Location> locations;

    @ManyToMany private List<CloudCredential> cloudCredentials;

    @OneToMany(mappedBy = "hardware", cascade = CascadeType.REMOVE)
    private List<VirtualMachineTemplate> virtualMachineTemplates;

    /**
     * Empty constructor for hibernate.
     */
    private Hardware() {
    }

    public Hardware(String cloudUuid, Cloud cloud, HardwareOffer hardwareOffer, List<Location> locations) {
        this.cloudUuid = cloudUuid;
        this.cloud = cloud;
        this.hardwareOffer = hardwareOffer;
        this.locations = locations;
    }

    public String getCloudUuid() {
        return cloudUuid;
    }

    public void setCloudUuid(String cloudUuid) {
        this.cloudUuid = cloudUuid;
    }

    public HardwareOffer getHardwareOffer() {
        return hardwareOffer;
    }

    public void setHardwareOffer(HardwareOffer hardwareOffer) {
        this.hardwareOffer = hardwareOffer;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        this.cloud = cloud;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
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
}
