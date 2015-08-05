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

package cloud.colosseum;


import de.uniulm.omi.cloudiator.sword.api.domain.TemplateOptions;
import models.*;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

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
    private TemplateOptions templateOptions;

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
        //todo: implement
        throw new UnsupportedOperationException();
    }

    public ColosseumVirtualMachineTemplateBuilder templateOptions(TemplateOptions templateOptions) {
        this.templateOptions = templateOptions;
        return this;
    }

    public ColosseumVirtualMachineTemplateBuilder virtualMachineModel(
        VirtualMachine virtualMachine) {
        checkNotNull(virtualMachine);
        checkArgument(virtualMachine.getCloudCredentials().size() == 1);
        this.cloud = virtualMachine.cloud();
        this.image = virtualMachine.image();
        this.location = virtualMachine.location();
        this.hardware = virtualMachine.hardware();
        this.cloudCredential = virtualMachine.getCloudCredentials().get(0);
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

    public ColosseumVirtualMachineTemplate build() {
        return new BaseColosseumVirtualMachineTemplate(cloud, cloudCredential, getImage(),
            getHardware(), getLocation(), templateOptions);
    }


}
