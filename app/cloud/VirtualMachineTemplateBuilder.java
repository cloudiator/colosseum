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

package cloud;

import models.*;

import javax.annotation.Nullable;

/**
 * Created by daniel on 12.02.15.
 */
public class VirtualMachineTemplateBuilder {

    private Cloud cloud;
    private Hardware hardware;
    private Location location;
    private Image image;
    private CloudLocation cloudLocation;
    private CloudImage cloudImage;
    private CloudHardware cloudHardware;

    VirtualMachineTemplateBuilder() {
    }

    public VirtualMachineTemplateBuilder cloud(Cloud cloud) {
        this.cloud = cloud;
        return this;
    }

    public VirtualMachineTemplateBuilder hardware(Hardware hardware) {
        this.hardware = hardware;
        return this;
    }

    public VirtualMachineTemplateBuilder location(Location location) {
        this.location = location;
        return this;
    }

    public VirtualMachineTemplateBuilder image(Image image) {
        this.image = image;
        return this;
    }

    public VirtualMachineTemplateBuilder cloudLocation(CloudLocation cloudLocation) {
        this.cloudLocation = cloudLocation;
        return this;
    }

    public VirtualMachineTemplateBuilder cloudHardware(CloudHardware cloudHardware) {
        this.cloudHardware = cloudHardware;
        return this;
    }

    public VirtualMachineTemplateBuilder cloudImage(CloudImage cloudImage) {
        this.cloudImage = cloudImage;
        return this;
    }

    public VirtualMachineTemplateBuilder virtualMachineTemplateModel(models.VirtualMachineTemplate virtualMachineTemplateModel) {
        this.image = virtualMachineTemplateModel.getImage();
        this.location = virtualMachineTemplateModel.getLocation();
        this.hardware = virtualMachineTemplateModel.getHardware();
        return this;
    }

    @Nullable
    protected CloudHardware getCloudHardware() {
        if (cloudHardware != null) {
            return cloudHardware;
        }
        if (cloud != null && hardware != null) {
            for (CloudHardware searchHardware : cloud.getCloudHardware()) {
                if (searchHardware.getHardware().equals(hardware)) {
                    return searchHardware;
                }
            }
        }
        return null;
    }

    @Nullable
    protected CloudImage getCloudImage() {
        if (cloudImage != null) {
            return cloudImage;
        }
        if (cloud != null && image != null) {
            for (CloudImage searchImage : cloud.getCloudImages()) {
                if (searchImage.getImage().equals(image)) {
                    return searchImage;
                }
            }
        }

        return null;
    }

    @Nullable
    protected CloudLocation getCloudLocation() {
        if (cloudLocation != null) {
            return cloudLocation;
        }
        if (cloud != null && location != null) {
            for (CloudLocation searchLocation : cloud.getCloudLocations()) {
                if (searchLocation.getLocation().equals(location)) {
                    return searchLocation;
                }
            }
        }
        return null;
    }

    public VirtualMachineTemplate build() {
        return new VirtualMachineTemplate(getCloudImage(), getCloudHardware(), getCloudLocation());
    }


}
