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

import dtos.HardwareDto;
import dtos.builders.HardwareDtoBuilder;
import models.Hardware;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by daniel seybold on 10.12.2014.
 */
public class HardwareConverter extends BaseConverter<Hardware, HardwareDto> {

    /**
     * Sets the dto to the hardware model.
     *
     * @param hardware    the hardware model where the dto should be set.
     * @param hardwareDto the dto to be set.
     * @return the merged hardware object.
     */
    protected Hardware setDto(Hardware hardware, HardwareDto hardwareDto) {
        hardware.setNumberOfCpu(hardwareDto.numberOfCpu);
        hardware.setMbOfRam(hardwareDto.mbOfRam);
        hardware.setLocalDiskSpace(hardwareDto.localDiskSpace);

        return hardware;
    }

    @Override
    public Hardware toModel(HardwareDto dto) {
        checkNotNull(dto);
        return setDto(new Hardware(), dto);
    }

    @Override
    public Hardware toModel(HardwareDto dto, Hardware model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public HardwareDto toDto(Hardware model) {
        checkNotNull(model);
        return new HardwareDtoBuilder()
                .numberOfCpu(model.getNumberOfCpu())
                .mbOfRam(model.getMbOfRam())
                .localDiskSpace(model.getLocalDiskSpace())
                .build();
    }
}
