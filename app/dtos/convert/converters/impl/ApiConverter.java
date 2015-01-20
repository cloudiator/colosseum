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

import dtos.ApiDto;
import models.Api;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class ApiConverter extends BaseConverter<Api, ApiDto> {

    /**
     * Sets the dto to the cloud model.
     *
     * @param api    the cloud model where the dto should be set.
     * @param apiDto the dto to be set.
     * @return the merged cloud object.
     */
    protected Api setDto(Api api, ApiDto apiDto) {
        api.setName(apiDto.name);
        return api;
    }

    @Override
    public Api toModel(ApiDto dto) {
        checkNotNull(dto);
        return this.setDto(new Api(), dto);
    }

    @Override
    public Api toModel(ApiDto dto, Api model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public ApiDto toDto(Api model) {
        checkNotNull(model);
        return new ApiDto(model.getName());
    }
}
