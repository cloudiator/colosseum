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

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * The model class Cloud.
 * <p>
 * Represents a specific cloud like amazon ec2, flexiant etc.
 *
 * @author Daniel Baur
 */
@Entity
public class Cloud extends NamedModel {

    /**
     * Empty constructor. Needed by hibernate.
     */
    public Cloud() {
    }

    public Cloud(String name) {
        super(name);
    }

    /**
     * Serial Version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * API information about this cloud.
     */
    @OneToMany
    public List<CloudApi> cloudApis;

    /**
     * The concrete images available in this cloud.
     */
    @OneToMany(mappedBy = "cloud")
    private List<CloudImage> cloudImages;

    /**
     * The concrete locations available in this cloud.
     */
    @OneToMany(mappedBy = "cloud")
    private List<CloudLocation> cloudLocations;

    /**
     * The concrete hardware flavors available in this cloud.
     */
    @OneToMany(mappedBy = "cloud")
    private List<CloudHardware> cloudHardware;

    @OneToMany(mappedBy = "cloud")
    private List<VirtualMachineTemplate> virtualMachineTemplates;


    public List<CloudApi> getCloudApis() {
        return cloudApis;
    }

    public void setCloudApis(List<CloudApi> cloudApis) {
        this.cloudApis = cloudApis;
    }

    public List<CloudImage> getCloudImages() {
        return cloudImages;
    }

    public void setCloudImages(List<CloudImage> cloudImages) {
        this.cloudImages = cloudImages;
    }

    public List<CloudLocation> getCloudLocations() {
        return cloudLocations;
    }

    public void setCloudLocations(List<CloudLocation> cloudLocations) {
        this.cloudLocations = cloudLocations;
    }

    public List<CloudHardware> getCloudHardware() {
        return cloudHardware;
    }

    public void setCloudHardware(List<CloudHardware> cloudHardware) {
        this.cloudHardware = cloudHardware;
    }

    public List<VirtualMachineTemplate> getVirtualMachineTemplates() {
        return virtualMachineTemplates;
    }

    public void setVirtualMachineTemplates(List<VirtualMachineTemplate> virtualMachineTemplates) {
        this.virtualMachineTemplates = virtualMachineTemplates;
    }
}
