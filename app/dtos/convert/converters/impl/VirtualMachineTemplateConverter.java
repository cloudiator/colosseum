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
import models.service.api.CloudHardwareService;
import models.service.api.CloudImageService;
import models.service.api.CloudLocationService;
import models.service.api.CloudService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 12.02.15.
 */
public class VirtualMachineTemplateConverter extends BaseConverter<VirtualMachineTemplate, VirtualMachineTemplateDto> {

    private final CloudService cloudService;
    private final CloudImageService cloudImageService;
    private final CloudLocationService cloudLocationService;
    private final CloudHardwareService cloudHardwareService;

    @Inject
    public VirtualMachineTemplateConverter(CloudService cloudService, CloudImageService cloudImageService, CloudLocationService cloudLocationService, CloudHardwareService cloudHardwareService) {

        checkNotNull(cloudService);
        checkNotNull(cloudImageService);
        checkNotNull(cloudLocationService);
        checkNotNull(cloudHardwareService);

        this.cloudService = cloudService;
        this.cloudImageService = cloudImageService;
        this.cloudLocationService = cloudLocationService;
        this.cloudHardwareService = cloudHardwareService;
    }

    protected VirtualMachineTemplate setDto(VirtualMachineTemplate model, VirtualMachineTemplateDto dto) {
        Cloud cloud = cloudService.getById(dto.cloud);
        checkNotNull(cloud, "Could not retrieve cloud for id = " + cloud);
        model.setCloud(cloud);

        CloudImage cloudImage = cloudImageService.getById(dto.cloudImage);
        checkNotNull(cloudImage, "Could not retrieve cloudImage for id = " + cloudImage);
        model.setCloudImage(cloudImage);

        CloudLocation cloudLocation = cloudLocationService.getById(dto.cloudLocation);
        checkNotNull(cloudLocation, "Could not retrieve cloudLocation for id = " + cloudLocation);
        model.setCloudLocation(cloudLocation);

        CloudHardware cloudHardware = cloudHardwareService.getById(dto.cloudHardware);
        checkNotNull(cloudHardware, "Could not retrieve cloudHardware for id = " + cloudHardware);
        model.setCloudHardware(cloudHardware);

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
