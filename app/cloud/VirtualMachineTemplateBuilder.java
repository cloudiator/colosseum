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
    private HardwareOffer hardwareOffer;
    private LocationOffer locationOffer;
    private ImageOffer imageOffer;
    private Location location;
    private Image image;
    private Hardware hardware;

    VirtualMachineTemplateBuilder() {
    }

    public VirtualMachineTemplateBuilder cloud(Cloud cloud) {
        this.cloud = cloud;
        return this;
    }

    public VirtualMachineTemplateBuilder hardware(HardwareOffer hardwareOffer) {
        this.hardwareOffer = hardwareOffer;
        return this;
    }

    public VirtualMachineTemplateBuilder location(LocationOffer locationOffer) {
        this.locationOffer = locationOffer;
        return this;
    }

    public VirtualMachineTemplateBuilder image(ImageOffer imageOffer) {
        this.imageOffer = imageOffer;
        return this;
    }

    public VirtualMachineTemplateBuilder cloudLocation(Location location) {
        this.location = location;
        return this;
    }

    public VirtualMachineTemplateBuilder cloudHardware(Hardware hardware) {
        this.hardware = hardware;
        return this;
    }

    public VirtualMachineTemplateBuilder cloudImage(Image image) {
        this.image = image;
        return this;
    }

    public VirtualMachineTemplateBuilder virtualMachineTemplateModel(
        models.VirtualMachineTemplate virtualMachineTemplateModel) {
        this.image = virtualMachineTemplateModel.getImage();
        this.location = virtualMachineTemplateModel.getLocation();
        this.hardware = virtualMachineTemplateModel.getHardware();
        return this;
    }

    @Nullable protected Hardware getHardware() {
        if (hardware != null) {
            return hardware;
        }
        if (cloud != null && hardwareOffer != null) {
            for (Hardware searchHardware : cloud.getHardware()) {
                if (searchHardware.getHardwareOffer().equals(hardwareOffer)) {
                    return searchHardware;
                }
            }
        }
        return null;
    }

    @Nullable protected Image getImage() {
        if (image != null) {
            return image;
        }
        if (cloud != null && imageOffer != null) {
            for (Image searchImage : cloud.getImages()) {
                if (searchImage.getImageOffer().equals(imageOffer)) {
                    return searchImage;
                }
            }
        }

        return null;
    }

    @Nullable protected Location getLocation() {
        if (location != null) {
            return location;
        }
        if (cloud != null && locationOffer != null) {
            for (Location searchLocation : cloud.getLocations()) {
                if (searchLocation.getLocationOffer().equals(locationOffer)) {
                    return searchLocation;
                }
            }
        }
        return null;
    }

    public VirtualMachineTemplate build() {
        return new VirtualMachineTemplate(getImage(), getHardware(), getLocation());
    }


}
