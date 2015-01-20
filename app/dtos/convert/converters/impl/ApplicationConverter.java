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

import dtos.ApplicationDto;
import models.Application;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by daniel seybold on 16.12.2014.
 */
public class ApplicationConverter extends BaseConverter<Application, ApplicationDto> {

    /**
     * Sets the dto to the application model.
     *
     * @param application    the application model where the dto should be set.
     * @param applicationDto the dto to be set.
     * @return the merged application object.
     */
    protected Application setDto(Application application, ApplicationDto applicationDto) {
        application.setName(applicationDto.name);
        return application;
    }

    @Override
    public Application toModel(ApplicationDto dto) {
        checkNotNull(dto);
        return this.setDto(new Application(), dto);
    }


    @Override
    public Application toModel(ApplicationDto dto, Application model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public ApplicationDto toDto(Application model) {
        checkNotNull(model);
        return new ApplicationDto(model.getName());
    }
}
