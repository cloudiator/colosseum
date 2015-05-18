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

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by daniel on 11.02.15.
 */
@Entity public class VirtualMachineTemplate extends Model {

    @OneToMany(mappedBy = "virtualMachineTemplate") private List<ApplicationComponent>
        applicationComponents;

    @ManyToOne(optional = false) private Cloud cloud;

    @ManyToOne(optional = false) private Image image;

    @ManyToOne(optional = false) private Location location;

    @ManyToOne(optional = false) private Hardware hardware;

    /**
     * Empty constructor for hibernate.
     */
    private VirtualMachineTemplate() {
    }

    public VirtualMachineTemplate(Cloud cloud, Image image, Location location, Hardware hardware) {
        this.cloud = cloud;
        this.image = image;
        this.location = location;
        this.hardware = hardware;
    }

    public List<ApplicationComponent> getApplicationComponents() {
        return applicationComponents;
    }

    public void setApplicationComponents(List<ApplicationComponent> applicationComponents) {
        this.applicationComponents = applicationComponents;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Hardware getHardware() {
        return hardware;
    }

    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        this.cloud = cloud;
    }
}
