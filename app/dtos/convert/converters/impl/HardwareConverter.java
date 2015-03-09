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
import dtos.convert.impl.BaseConverter;
import models.Hardware;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by daniel seybold on 10.12.2014.
 */
public class HardwareConverter extends BaseConverter<Hardware, HardwareDto> {

    @Override
    public Hardware toModel(HardwareDto dto, Hardware model) {
        checkNotNull(dto);
        checkNotNull(model);
        model.setNumberOfCpu(dto.getNumberOfCpu());
        model.setMbOfRam(dto.getMbOfRam());
        model.setLocalDiskSpace(dto.getLocalDiskSpace());

        return model;
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
