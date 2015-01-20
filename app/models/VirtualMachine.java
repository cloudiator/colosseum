/*
 * Copyright (c) 2015 University of Ulm
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 31.10.14.
 */
@Entity
public class VirtualMachine extends NamedModel {

    public VirtualMachine() {
    }

    @ManyToOne(optional = false)
    private Cloud cloud;

    @ManyToOne(optional = false)
    private CloudImage cloudImage;

    @ManyToOne(optional = false)
    private CloudHardware cloudHardware;

    @ManyToOne(optional = false)
    private CloudLocation cloudLocation;

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        checkNotNull(cloud);
        this.cloud = cloud;
    }

    public CloudImage getCloudImage() {
        return cloudImage;
    }

    public void setCloudImage(CloudImage cloudImage) {
        checkNotNull(cloudImage);
        this.cloudImage = cloudImage;
    }

    public CloudHardware getCloudHardware() {
        return cloudHardware;
    }

    public void setCloudHardware(CloudHardware cloudHardware) {
        checkNotNull(cloudHardware);
        this.cloudHardware = cloudHardware;
    }

    public CloudLocation getCloudLocation() {
        return cloudLocation;
    }

    public void setCloudLocation(CloudLocation cloudLocation) {
        checkNotNull(cloudLocation);
        this.cloudLocation = cloudLocation;
    }
}
