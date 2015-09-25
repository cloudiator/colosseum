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
        checkArgument(virtualMachine.image().isPresent());
        this.image = virtualMachine.image().get();
        checkArgument(virtualMachine.cloudCredentials().size() == 1);
        this.cloudCredential = virtualMachine.cloudCredentials().get(0);
        this.cloud = virtualMachine.cloud();
        this.location = virtualMachine.location();
        checkArgument(virtualMachine.hardware().isPresent());
        this.hardware = virtualMachine.hardware().get();
        return this;
    }


    @Nullable protected Hardware getHardware() {
        if (hardware != null) {
            return hardware;
        }
        if (cloud != null && hardwareOffer != null) {
            for (Hardware searchHardware : cloud.hardware()) {
                if (searchHardware.hardwareOffer().equals(hardwareOffer)) {
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
            for (Image searchImage : cloud.images()) {
                if (searchImage.operatingSystem().isPresent() && operatingSystem
                    .equals(searchImage.operatingSystem().get())) {
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
            for (Location searchLocation : cloud.locations()) {
                if (searchLocation.geoLocation().isPresent() && geoLocation
                    .equals(searchLocation.geoLocation().get())) {
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
