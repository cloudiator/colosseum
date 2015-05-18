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
public class ColosseumVirtualMachineTemplateBuilder {

    private Cloud cloud;
    private CloudCredential cloudCredential;
    private HardwareOffer hardwareOffer;
    private GeoLocation geoLocation;
    private OperatingSystem operatingSystem;
    private Location location;
    private Image image;
    private Hardware hardware;

    ColosseumVirtualMachineTemplateBuilder() {
    }

    public ColosseumVirtualMachineTemplateBuilder cloud(Cloud cloud) {
        this.cloud = cloud;
        return this;
    }

    public ColosseumVirtualMachineTemplateBuilder cloudCredential(CloudCredential cloudCredential) {
        this.cloudCredential = cloudCredential;
        return this;
    }

    public ColosseumVirtualMachineTemplateBuilder hardware(HardwareOffer hardwareOffer) {
        this.hardwareOffer = hardwareOffer;
        return this;
    }

    public ColosseumVirtualMachineTemplateBuilder geoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
        return this;
    }

    public ColosseumVirtualMachineTemplateBuilder os(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
        return this;
    }

    public ColosseumVirtualMachineTemplateBuilder cloudLocation(Location location) {
        this.location = location;
        return this;
    }

    public ColosseumVirtualMachineTemplateBuilder cloudHardware(Hardware hardware) {
        this.hardware = hardware;
        return this;
    }

    public ColosseumVirtualMachineTemplateBuilder cloudImage(Image image) {
        this.image = image;
        return this;
    }

    public ColosseumVirtualMachineTemplateBuilder virtualMachineTemplateModel(
        models.VirtualMachineTemplate virtualMachineTemplateModel) {
        this.cloud = virtualMachineTemplateModel.getCloud();
        this.image = virtualMachineTemplateModel.getImage();
        this.location = virtualMachineTemplateModel.getLocation();
        this.hardware = virtualMachineTemplateModel.getHardware();
        return this;
    }

    public ColosseumVirtualMachineTemplateBuilder virtualMachineModel(
        VirtualMachine virtualMachine) {
        this.cloud = virtualMachine.getCloud();
        this.image = virtualMachine.getImage();
        this.location = virtualMachine.getLocation();
        this.hardware = virtualMachine.getHardware();
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
                if (operatingSystem.equals(searchImage.getOperatingSystem())) {
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

    @Nullable protected CloudCredential getCloudCredential() {
        if (cloudCredential != null) {
            return cloudCredential;
        }
        if (cloud != null && image != null && location != null && hardware != null) {
            for (CloudCredential inCloud : cloud.getCloudCredentials()) {
                for (CloudCredential inImage : image.getCloudCredentials()) {
                    for (CloudCredential inLocation : location.getCloudCredentials()) {
                        for (CloudCredential inHardware : hardware.getCloudCredentials()) {
                            if (inCloud.equals(inImage) && inCloud.equals(inLocation) && inCloud
                                .equals(inHardware)) {
                                return inCloud;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public ColosseumVirtualMachineTemplate build() {
        return new ColosseumVirtualMachineTemplate(cloud, getCloudCredential(), getImage(),
            getHardware(), getLocation());
    }


}
