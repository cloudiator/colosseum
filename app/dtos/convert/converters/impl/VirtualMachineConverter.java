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
import dtos.VirtualMachineDto;
import dtos.builders.VirtualMachineDtoBuilder;
import dtos.convert.impl.BaseConverter;
import models.*;
import models.service.impl.CloudHardwareServiceImpl;
import models.service.impl.CloudImageServiceImpl;
import models.service.impl.CloudLocationServiceImpl;
import models.service.impl.CloudServiceImpl;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel seybold on 09.12.2014.
 */
public class VirtualMachineConverter extends BaseConverter<VirtualMachine, VirtualMachineDto> {

    private final CloudServiceImpl cloudServiceImpl;
    private final CloudLocationServiceImpl cloudLocationServiceImpl;
    private final CloudHardwareServiceImpl cloudHardwareFlavorServiceImpl;
    private final CloudImageServiceImpl cloudImageServiceImpl;

    @Inject
    public VirtualMachineConverter(CloudServiceImpl cloudServiceImpl, CloudLocationServiceImpl cloudLocationServiceImpl, CloudHardwareServiceImpl cloudHardwareFlavorServiceImpl, CloudImageServiceImpl cloudImageServiceImpl) {

        checkNotNull(cloudServiceImpl);
        checkNotNull(cloudLocationServiceImpl);
        checkNotNull(cloudHardwareFlavorServiceImpl);
        checkNotNull(cloudImageServiceImpl);

        this.cloudServiceImpl = cloudServiceImpl;
        this.cloudLocationServiceImpl = cloudLocationServiceImpl;
        this.cloudHardwareFlavorServiceImpl = cloudHardwareFlavorServiceImpl;
        this.cloudImageServiceImpl = cloudImageServiceImpl;
    }

    @Override
    public VirtualMachine toModel(VirtualMachineDto dto, VirtualMachine model) {
        checkNotNull(dto);
        checkNotNull(model);
        model.setName(dto.getName());

        Cloud cloud = cloudServiceImpl.getById(dto.getCloud());
        checkState(cloud != null, "Could not retrieve cloud for id: " + dto.getCloud());
        model.setCloud(cloud);

        CloudLocation cloudLocation = cloudLocationServiceImpl.getById(dto.getCloudLocation());
        checkState(cloudLocation != null, "Could not retrieve cloudLocation for id: " + dto.getCloudLocation());
        model.setCloudLocation(cloudLocation);

        CloudHardware cloudHardware = cloudHardwareFlavorServiceImpl.getById(dto.getCloudHardware());
        checkState(cloudHardware != null, "Could not retrieve cloudHardware for id: " + dto.getCloudHardware());
        model.setCloudHardware(cloudHardware);

        CloudImage cloudImage = cloudImageServiceImpl.getById(dto.getCloudImage());
        checkState(cloudImage != null, "Could not retrieve cloudImage for id: " + dto.getCloudImage());
        model.setCloudImage(cloudImage);

        return model;
    }

    @Override
    public VirtualMachineDto toDto(VirtualMachine model) {
        checkNotNull(model);
        return new VirtualMachineDtoBuilder()
                .name(model.getName())
                .cloud(model.getCloud().getId())
                .cloudImage(model.getCloudImage().getId())
                .cloudHardware(model.getCloudHardware().getId())
                .cloudLocation(model.getCloudLocation().getId())
                .build();
    }

}
