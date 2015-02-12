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
import dtos.CloudApiDto;
import dtos.builders.CloudApiDtoBuilder;
import models.Api;
import models.Cloud;
import models.CloudApi;
import models.service.impl.ApiServiceImpl;
import models.service.impl.CloudServiceImpl;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class CloudApiConverter extends BaseConverter<CloudApi, CloudApiDto> {

    private final ApiServiceImpl apiServiceImpl;
    private final CloudServiceImpl cloudServiceImpl;

    @Inject
    CloudApiConverter(ApiServiceImpl apiServiceImpl, CloudServiceImpl cloudServiceImpl) {

        checkNotNull(apiServiceImpl);
        checkNotNull(cloudServiceImpl);

        this.apiServiceImpl = apiServiceImpl;
        this.cloudServiceImpl = cloudServiceImpl;

    }

    protected CloudApi setDto(CloudApi cloudApi, CloudApiDto cloudApiDto) {
        cloudApi.setEndpoint(cloudApiDto.endpoint);

        Api api = apiServiceImpl.getById(cloudApiDto.api);
        checkState(api != null, "Could not retrieve api for id: " + cloudApiDto.api);
        cloudApi.setApi(api);

        Cloud cloud = cloudServiceImpl.getById(cloudApiDto.cloud);
        checkState(cloud != null, "Could not retrieve cloud for id: " + cloudApiDto.cloud);
        cloudApi.setCloud(cloud);

        return cloudApi;
    }

    @Override
    public CloudApi toModel(CloudApiDto dto) {
        checkNotNull(dto);
        return this.setDto(new CloudApi(), dto);
    }

    @Override
    public CloudApi toModel(CloudApiDto dto, CloudApi model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public CloudApiDto toDto(CloudApi model) {
        checkNotNull(model);
        return new CloudApiDtoBuilder()
                .api(model.getApi().getId())
                .cloud(model.getCloud().getId())
                .endpoint(model.getEndpoint())
                .build();
    }
}
