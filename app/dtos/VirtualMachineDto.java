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

import dtos.generic.RemoteDto;
import dtos.validation.validators.ModelIdValidator;
import dtos.validation.validators.NotNullOrEmptyValidator;
import dtos.validation.validators.NotNullValidator;
import models.Cloud;
import models.Hardware;
import models.Image;
import models.Location;
import models.TemplateOptions;
import models.service.ModelService;

/**
 * Created by bwpc on 09.12.2014.
 */
public class VirtualMachineDto extends RemoteDto {

    private String name;
    private Long cloud;
    private Long image;
    private Long hardware;
    private Long location;
    private Long templateOptions;

    public VirtualMachineDto() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCloud() {
        return cloud;
    }

    public void setCloud(Long cloud) {
        this.cloud = cloud;
    }

    public Long getImage() {
        return image;
    }

    public void setImage(Long image) {
        this.image = image;
    }

    public Long getHardware() {
        return hardware;
    }

    public void setHardware(Long hardware) {
        this.hardware = hardware;
    }

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long location) {
        this.location = location;
    }

    public Long getTemplateOptions() {
        return templateOptions;
    }

    public void setTemplateOptions(Long templateOptions) {
        this.templateOptions = templateOptions;
    }

    @Override public void validation() {
        super.validation();
        validator(String.class).validate(this.name).withValidator(new NotNullOrEmptyValidator());
        validator(Long.class).validate(cloud, "cloud").withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.cloudService.get()));
        validator(Long.class).validate(image, "image").withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.imageService.get()));
        validator(Long.class).validate(location, "location").withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.locationService.get()));
        validator(Long.class).validate(hardware, "hardware").withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.hardwareService.get()));
        validator(Long.class).validate(templateOptions, "templateOptions")
            .withValidator(new ModelIdValidator<>(References.templateOptionsService.get()));
    }

    public static class References {

        @Inject private static Provider<ModelService<Cloud>> cloudService;
        @Inject private static Provider<ModelService<Image>> imageService;
        @Inject private static Provider<ModelService<Location>> locationService;
        @Inject private static Provider<ModelService<Hardware>> hardwareService;
        @Inject private static Provider<ModelService<TemplateOptions>> templateOptionsService;

        private References() {
        }
    }
}
