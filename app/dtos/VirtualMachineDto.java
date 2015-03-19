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

package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.impl.NamedDto;
import models.Cloud;
import models.Hardware;
import models.Image;
import models.Location;
import models.service.api.CloudHardwareService;
import models.service.api.CloudImageService;
import models.service.api.CloudLocationService;
import models.service.impl.generic.BaseModelService;
import play.data.validation.ValidationError;

import java.util.List;

/**
 * Created by bwpc on 09.12.2014.
 */
public class VirtualMachineDto extends NamedDto {

    protected Long cloud;
    protected Long cloudImage;
    protected Long cloudHardware;
    protected Long cloudLocation;

    public VirtualMachineDto() {
        super();
    }

    public VirtualMachineDto(String name, Long cloud, Long cloudImage, Long cloudHardware,
        Long cloudLocation) {
        super(name);
        this.cloud = cloud;
        this.cloudImage = cloudImage;
        this.cloudHardware = cloudHardware;
        this.cloudLocation = cloudLocation;
    }

    public Long getCloud() {
        return cloud;
    }

    public void setCloud(Long cloud) {
        this.cloud = cloud;
    }

    public Long getCloudImage() {
        return cloudImage;
    }

    public void setCloudImage(Long cloudImage) {
        this.cloudImage = cloudImage;
    }

    public Long getCloudHardware() {
        return cloudHardware;
    }

    public void setCloudHardware(Long cloudHardware) {
        this.cloudHardware = cloudHardware;
    }

    public Long getCloudLocation() {
        return cloudLocation;
    }

    public void setCloudLocation(Long cloudLocation) {
        this.cloudLocation = cloudLocation;
    }

    @Override public List<ValidationError> validateNotNull() {
        final List<ValidationError> errors = super.validateNotNull();

        //validate cloud reference
        Cloud cloud = null;
        if (this.cloud == null) {
            errors.add(new ValidationError("cloud", "The cloud is required."));
        } else {
            cloud = References.cloudService.get().getById(this.cloud);
            if (cloud == null) {
                errors.add(new ValidationError("cloud", "The given cloud is invalid."));
            }
        }

        //validate the cloud image
        if (this.cloudImage == null) {
            errors.add(new ValidationError("cloudImage", "The cloud image is required."));
        } else {
            final Image image = References.cloudImageService.get().getById(this.cloudImage);
            if (image == null) {
                errors.add(new ValidationError("cloudImage", "The given cloud image is invalid."));
            } else {
                if (!image.getCloud().equals(cloud)) {
                    errors.add(new ValidationError("cloudImage",
                        "The cloud image does not belong to the specified cloud."));
                }
            }
        }

        //validate the cloud hardware
        if (this.cloudHardware == null) {
            errors.add(new ValidationError("cloudHardware", "The cloud hardware is required."));
        } else {
            final Hardware hardware =
                References.cloudHardwareFlavorService.get().getById(this.cloudHardware);
            if (hardware == null) {
                errors.add(
                    new ValidationError("cloudHardware", "The given cloud hardware is invalid"));
            } else {
                if (!hardware.getCloud().equals(cloud)) {
                    errors.add(new ValidationError("cloudHardware",
                        "The cloud hardware does not belong to the specified cloud."));
                }
            }
        }

        //validate the cloud location
        if (this.cloudLocation == null) {
            errors.add(new ValidationError("cloudLocation", "The cloud location is required."));
        } else {
            final Location location =
                References.cloudLocationService.get().getById(this.cloudLocation);
            if (location == null) {
                errors.add(
                    new ValidationError("cloudLocation", "The given cloud location is invalid."));
            } else {
                if (!location.getCloud().equals(cloud)) {
                    errors.add(new ValidationError("cloudLocation",
                        "The cloud location does not belong to the specified cloud."));
                }
            }
        }

        return errors;
    }


    public static class References {
        @Inject public static Provider<BaseModelService<Cloud>> cloudService;
        @Inject public static Provider<CloudImageService> cloudImageService;
        @Inject public static Provider<CloudHardwareService> cloudHardwareFlavorService;
        @Inject public static Provider<CloudLocationService> cloudLocationService;
    }
}
