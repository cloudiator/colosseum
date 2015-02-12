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

package dtos.convert.converters.impl;

import com.google.inject.Inject;
import dtos.VirtualMachineTemplateDto;
import models.*;
import models.service.api.CloudService;
import models.service.api.HardwareService;
import models.service.api.ImageService;
import models.service.api.LocationService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 12.02.15.
 */
public class VirtualMachineTemplateConverter extends BaseConverter<VirtualMachineTemplate, VirtualMachineTemplateDto> {

    private final CloudService cloudService;
    private final ImageService imageService;
    private final LocationService locationService;
    private final HardwareService hardwareService;

    @Inject
    public VirtualMachineTemplateConverter(CloudService cloudService, ImageService imageService, LocationService locationService, HardwareService hardwareService) {

        checkNotNull(cloudService);
        checkNotNull(imageService);
        checkNotNull(locationService);
        checkNotNull(hardwareService);

        this.cloudService = cloudService;
        this.imageService = imageService;
        this.locationService = locationService;
        this.hardwareService = hardwareService;
    }

    protected VirtualMachineTemplate setDto(VirtualMachineTemplate model, VirtualMachineTemplateDto dto) {
        Cloud cloud = cloudService.getById(dto.cloud);
        checkNotNull(cloud, "Could not retrieve cloud for id = " + cloud);
        model.setCloud(cloud);

        Image image = imageService.getById(dto.image);
        checkNotNull(image, "Could not retrieve cloud for id = " + image);
        model.setImage(image);

        Location location = locationService.getById(dto.location);
        checkNotNull(location, "Could not retrieve cloud for id = " + location);
        model.setLocation(location);

        Hardware hardware = hardwareService.getById(dto.hardware);
        checkNotNull(hardware, "Could not retrieve hardware for id = " + hardware);
        model.setHardware(hardware);

        return model;
    }

    @Override
    public VirtualMachineTemplate toModel(VirtualMachineTemplateDto dto) {
        checkNotNull(dto);
        return this.setDto(new VirtualMachineTemplate(), dto);
    }

    @Override
    public VirtualMachineTemplate toModel(VirtualMachineTemplateDto dto, VirtualMachineTemplate model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public VirtualMachineTemplateDto toDto(VirtualMachineTemplate model) {
        checkNotNull(model);
        return null;
    }
}
