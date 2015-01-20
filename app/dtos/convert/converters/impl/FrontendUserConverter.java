/*
 * Copyright (c) 2015 University of Ulm
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

import dtos.FrontendUserDto;
import dtos.builders.FrontendUserDtoBuilder;
import models.FrontendUser;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by daniel seybold on 11.12.2014.
 */
public class FrontendUserConverter extends BaseConverter<FrontendUser, FrontendUserDto> {

    /**
     * Sets the dto to the frontendUser model.
     *
     * @param frontendUser    the frontendUser model where the dto should be set.
     * @param frontendUserDto the dto to be set.
     * @return the merged hardware object.
     */
    protected FrontendUser setDto(FrontendUser frontendUser, FrontendUserDto frontendUserDto) {
        frontendUser.setFirstName(frontendUserDto.firstName);
        frontendUser.setLastName(frontendUserDto.lastName);
        frontendUser.setMail(frontendUserDto.mail);
        frontendUser.setPassword(frontendUserDto.password);

        return frontendUser;
    }

    @Override
    public FrontendUser toModel(FrontendUserDto dto) {
        checkNotNull(dto);
        return this.setDto(new FrontendUser(), dto);
    }

    @Override
    public FrontendUser toModel(FrontendUserDto dto, FrontendUser model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public FrontendUserDto toDto(FrontendUser model) {
        checkNotNull(model);
        return new FrontendUserDtoBuilder()
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .mail(model.getMail())
                .password(model.getPassword())
                .build();
    }
}
