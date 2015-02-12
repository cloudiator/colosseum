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
import dtos.CloudLocationDto;
import dtos.builders.CloudLocationDtoBuilder;
import models.Cloud;
import models.CloudLocation;
import models.Location;
import models.service.impl.CloudServiceImpl;
import models.service.impl.LocationServiceImpl;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel seybold on 10.12.2014.
 */
public class CloudLocationConverter extends BaseConverter<CloudLocation, CloudLocationDto> {

    private final CloudServiceImpl cloudServiceImpl;
    private final LocationServiceImpl locationServiceImpl;

    @Inject
    CloudLocationConverter(CloudServiceImpl cloudServiceImpl, LocationServiceImpl locationServiceImpl) {

        checkNotNull(cloudServiceImpl);
        checkNotNull(locationServiceImpl);

        this.cloudServiceImpl = cloudServiceImpl;
        this.locationServiceImpl = locationServiceImpl;

    }

    protected CloudLocation setDto(CloudLocation cloudLocation, CloudLocationDto cloudLocationDto) {
        cloudLocation.setCloudUuid(cloudLocationDto.cloudUuid);

        Cloud cloud = cloudServiceImpl.getById(cloudLocationDto.cloud);
        checkState(cloud != null, "Could not retrieve cloud for id: " + cloudLocationDto.cloud);
        cloudLocation.setCloud(cloud);

        Location location = locationServiceImpl.getById(cloudLocationDto.cloud);
        checkState(location != null, "Could not retrieve location for id: " + cloudLocationDto.location);
        cloudLocation.setLocation(location);

        return cloudLocation;
    }

    @Override
    public CloudLocation toModel(CloudLocationDto dto) {
        checkNotNull(dto);
        return this.setDto(new CloudLocation(), dto);
    }

    @Override
    public CloudLocation toModel(CloudLocationDto dto, CloudLocation model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public CloudLocationDto toDto(CloudLocation model) {
        checkNotNull(model);
        return new CloudLocationDtoBuilder()
                .cloudUuid(model.getCloudUuid())
                .cloud(model.getCloud().getId())
                .location(model.getLocation().getId())
                .build();
    }
}
