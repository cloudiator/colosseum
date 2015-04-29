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

import models.generic.NamedModel;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity public class Cloud extends NamedModel {

    @OneToOne(mappedBy = "cloud", cascade = CascadeType.REMOVE, optional = false) private CloudApi
        cloudApi;
    @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE) private List<Image> images;
    @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE) private List<Location> locations;
    @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE) private List<Hardware> hardware;
    @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE)
    private List<VirtualMachineTemplate> virtualMachineTemplates;
    @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE) private List<VirtualMachine>
        virtualMachines;
    @OneToMany(mappedBy = "cloud", cascade = CascadeType.REMOVE) private List<CloudCredential>
        cloudCredentials;

    /**
     * Empty constructor. Needed by hibernate.
     */
    private Cloud() {
    }

    public CloudApi getCloudApi() {
        return cloudApi;
    }

    public void setCloudApi(CloudApi cloudApi) {
        this.cloudApi = cloudApi;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Hardware> getHardware() {
        return hardware;
    }

    public void setHardware(List<Hardware> hardware) {
        this.hardware = hardware;
    }

    public List<VirtualMachineTemplate> getVirtualMachineTemplates() {
        return virtualMachineTemplates;
    }

    public void setVirtualMachineTemplates(List<VirtualMachineTemplate> virtualMachineTemplates) {
        this.virtualMachineTemplates = virtualMachineTemplates;
    }

    public List<VirtualMachine> getVirtualMachines() {
        return virtualMachines;
    }

    public void setVirtualMachines(List<VirtualMachine> virtualMachines) {
        this.virtualMachines = virtualMachines;
    }

    public List<CloudCredential> getCloudCredentials() {
        return cloudCredentials;
    }

    public void setCloudCredentials(List<CloudCredential> cloudCredentials) {
        this.cloudCredentials = cloudCredentials;
    }
}
