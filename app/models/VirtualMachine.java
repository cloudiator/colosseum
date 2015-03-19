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

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 31.10.14.
 */
@Entity public class VirtualMachine extends NamedModel {

    @ManyToOne(optional = true) private Cloud cloud;
    @Nullable @ManyToOne(optional = true) private Image image;
    @Nullable @ManyToOne(optional = true) private Hardware hardware;
    @Nullable @ManyToOne(optional = true) private Location location;
    @OneToMany(mappedBy = "virtualMachine") private List<IpAddress> ipAddresses;
    @Nullable @OneToOne(mappedBy = "virtualMachine", optional = true) private CloudVirtualMachine
        cloudVirtualMachine;

    /**
     * Empty constructor for hibernate.
     */
    private VirtualMachine() {
    }

    public VirtualMachine(String name, Cloud cloud, Image image, Hardware hardware,
        Location location, CloudVirtualMachine cloudVirtualMachine) {
        super(name);
        this.cloud = cloud;
        this.image = image;
        this.hardware = hardware;
        this.location = location;
        this.cloudVirtualMachine = cloudVirtualMachine;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        checkNotNull(cloud);
        this.cloud = cloud;
    }

    @Nullable public Image getImage() {
        return image;
    }

    public void setImage(@Nullable Image image) {
        this.image = image;
    }

    @Nullable public Hardware getHardware() {
        return hardware;
    }

    public void setHardware(@Nullable Hardware hardware) {
        this.hardware = hardware;
    }

    @Nullable public Location getLocation() {
        return location;
    }

    public void setLocation(@Nullable Location location) {
        this.location = location;
    }

    public List<IpAddress> getIpAddresses() {
        return ipAddresses;
    }

    public void setIpAddresses(List<IpAddress> ipAddresses) {
        this.ipAddresses = ipAddresses;
    }

    @Nullable public CloudVirtualMachine getCloudVirtualMachine() {
        return cloudVirtualMachine;
    }

    public void setCloudVirtualMachine(@Nullable CloudVirtualMachine cloudVirtualMachine) {
        this.cloudVirtualMachine = cloudVirtualMachine;
    }
}
