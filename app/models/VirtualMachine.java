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
import javax.persistence.OneToOne;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 31.10.14.
 */
@Entity
public class VirtualMachine extends NamedModel {

    /**
     * Empty constructor for hibernate.
     */
    private VirtualMachine() {
    }

    public VirtualMachine(String name, Cloud cloud, CloudImage cloudImage, CloudHardware cloudHardware, CloudLocation cloudLocation, CloudVirtualMachine cloudVirtualMachine) {
        super(name);
        this.cloud = cloud;
        this.cloudImage = cloudImage;
        this.cloudHardware = cloudHardware;
        this.cloudLocation = cloudLocation;
        this.cloudVirtualMachine = cloudVirtualMachine;
    }

    @ManyToOne(optional = true)
    private Cloud cloud;

    @Nullable
    @ManyToOne(optional = true)
    private CloudImage cloudImage;

    @Nullable
    @ManyToOne(optional = true)
    private CloudHardware cloudHardware;

    @Nullable
    @ManyToOne(optional = true)
    private CloudLocation cloudLocation;

    @Nullable
    @OneToOne(mappedBy = "virtualMachine", optional = true)
    private CloudVirtualMachine cloudVirtualMachine;

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        checkNotNull(cloud);
        this.cloud = cloud;
    }

    @Nullable
    public CloudImage getCloudImage() {
        return cloudImage;
    }

    public void setCloudImage(@Nullable CloudImage cloudImage) {
        this.cloudImage = cloudImage;
    }

    @Nullable
    public CloudHardware getCloudHardware() {
        return cloudHardware;
    }

    public void setCloudHardware(@Nullable CloudHardware cloudHardware) {
        this.cloudHardware = cloudHardware;
    }

    @Nullable
    public CloudLocation getCloudLocation() {
        return cloudLocation;
    }

    public void setCloudLocation(@Nullable CloudLocation cloudLocation) {
        this.cloudLocation = cloudLocation;
    }

    @Nullable
    public CloudVirtualMachine getCloudVirtualMachine() {
        return cloudVirtualMachine;
    }

    public void setCloudVirtualMachine(@Nullable CloudVirtualMachine cloudVirtualMachine) {
        this.cloudVirtualMachine = cloudVirtualMachine;
    }
}
