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
public class ColloseumVirtualMachineTemplateBuilder {

    private Cloud cloud;
    private HardwareOffer hardwareOffer;
    private GeoLocation geoLocation;
    private OperatingSystem operatingSystem;
    private Location location;
    private Image image;
    private Hardware hardware;

    ColloseumVirtualMachineTemplateBuilder() {
    }

    public ColloseumVirtualMachineTemplateBuilder cloud(Cloud cloud) {
        this.cloud = cloud;
        return this;
    }

    public ColloseumVirtualMachineTemplateBuilder hardware(HardwareOffer hardwareOffer) {
        this.hardwareOffer = hardwareOffer;
        return this;
    }

    public ColloseumVirtualMachineTemplateBuilder geoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
        return this;
    }

    public ColloseumVirtualMachineTemplateBuilder os(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
        return this;
    }

    public ColloseumVirtualMachineTemplateBuilder cloudLocation(Location location) {
        this.location = location;
        return this;
    }

    public ColloseumVirtualMachineTemplateBuilder cloudHardware(Hardware hardware) {
        this.hardware = hardware;
        return this;
    }

    public ColloseumVirtualMachineTemplateBuilder cloudImage(Image image) {
        this.image = image;
        return this;
    }

    public ColloseumVirtualMachineTemplateBuilder virtualMachineTemplateModel(
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
        if (cloud != null && operatingSystem != null) {
            for (Image searchImage : cloud.getImages()) {
                if (searchImage.getOperatingSystem().equals(operatingSystem)) {
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
        if (cloud != null && geoLocation != null) {
            for (Location searchLocation : cloud.getLocations()) {
                if (geoLocation.equals(searchLocation.getGeoLocation())) {
                    return searchLocation;
                }
            }
        }
        return null;
    }

    public ColloseumVirtualMachineTemplate build() {
        return new ColloseumVirtualMachineTemplate(getImage(), getHardware(), getLocation());
    }


}
