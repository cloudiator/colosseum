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

import com.google.inject.Singleton;
import dtos.CloudDto;
import dtos.convert.converters.api.ModelDtoConverter;
import models.Cloud;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Converter implementation for the Cloud model and the CloudDto DTO.
 */
@Singleton
public class CloudConverter extends BaseConverter<Cloud, CloudDto> {

    /**
     * Sets the dto to the cloud model.
     *
     * @param cloud    the cloud model where the dto should be set.
     * @param cloudDto the dto to be set.
     * @return the merged cloud object.
     */
    protected Cloud setDto(Cloud cloud, CloudDto cloudDto) {
        cloud.setName(cloudDto.name);
        return cloud;
    }

    @Override
    public Cloud toModel(CloudDto cloudDto) {
        checkNotNull(cloudDto);
        return setDto(new Cloud(), cloudDto);
    }

    @Override
    public Cloud toModel(CloudDto cloudDto, Cloud model) {
        checkNotNull(cloudDto);
        checkNotNull(model);
        return setDto(model, cloudDto);
    }

    @Override
    public CloudDto toDto(Cloud cloud) {
        checkNotNull(cloud);
        return new CloudDto(cloud.getName());
    }
}
