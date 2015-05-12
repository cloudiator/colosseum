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

package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import dtos.generic.ValidatableDto;
import dtos.validation.IterableValidator;
import dtos.validation.ModelIdValidator;
import dtos.validation.NotNullOrEmptyValidator;
import models.FrontendUser;
import models.service.api.generic.ModelService;

import java.util.List;

/**
 * Created by daniel on 19.03.15.
 */
public class FrontendGroupDto extends ValidatableDto {

    private String name;
    private List<Long> frontendUsers;

    public FrontendGroupDto() {
    }

    public FrontendGroupDto(String name, List<Long> frontendUsers) {
        this.name = name;
        this.frontendUsers = frontendUsers;
    }

    public List<Long> getFrontendUsers() {
        return frontendUsers;
    }

    public void setFrontendUsers(List<Long> frontendUsers) {
        this.frontendUsers = frontendUsers;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override public void validation() {
        validator(String.class).validate(this.name).withValidator(new NotNullOrEmptyValidator());
        validator(new TypeLiteral<List<Long>>() {
        }).validate(frontendUsers).withValidator(
            new IterableValidator<>(new ModelIdValidator<>(References.frontendUserService.get())));
    }

    public static class References {
        @Inject public static Provider<ModelService<FrontendUser>> frontendUserService;
    }
}
