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
import dtos.generic.ValidatableDto;
import dtos.validation.ModelIdValidator;
import dtos.validation.NotNullValidator;
import models.Cloud;
import models.Hardware;
import models.Image;
import models.Location;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 12.02.15.
 */
public class VirtualMachineTemplateDto extends ValidatableDto {

    protected long cloud;
    protected long image;
    protected long location;
    protected long hardware;

    public VirtualMachineTemplateDto() {
        super();
    }

    public VirtualMachineTemplateDto(long cloud, long image, long location, long hardware) {
        this.cloud = cloud;
        this.image = image;
        this.location = location;
        this.hardware = hardware;
    }

    public long getCloud() {
        return cloud;
    }

    public void setCloud(long cloud) {
        this.cloud = cloud;
    }

    public long getImage() {
        return image;
    }

    public void setImage(long image) {
        this.image = image;
    }

    public long getLocation() {
        return location;
    }

    public void setLocation(long location) {
        this.location = location;
    }

    public long getHardware() {
        return hardware;
    }

    public void setHardware(long hardware) {
        this.hardware = hardware;
    }

    @Override public void validation() {
        validator(Long.class).validate(cloud).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.cloudService.get()));
        validator(Long.class).validate(image).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.imageService.get()));
        validator(Long.class).validate(location).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.locationService.get()));
        validator(Long.class).validate(hardware).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.hardwareService.get()));
    }

    public static class References {

        @Inject public static Provider<ModelService<Cloud>> cloudService;

        @Inject public static Provider<ModelService<Image>> imageService;

        @Inject public static Provider<ModelService<Location>> locationService;

        @Inject public static Provider<ModelService<Hardware>> hardwareService;
    }

}
