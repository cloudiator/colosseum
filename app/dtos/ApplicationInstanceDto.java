/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
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
import dtos.generic.ValidatableDto;
import dtos.validation.validators.ModelIdValidator;
import dtos.validation.validators.NotNullValidator;
import models.Application;
import models.service.BaseModelService;

/**
 * Created by daniel on 11.05.15.
 */
public class ApplicationInstanceDto extends ValidatableDto {

    protected Long application;

    public ApplicationInstanceDto() {
    }

    public ApplicationInstanceDto(Long application) {
        this.application = application;
    }

    @Override public void validation() {
        validator(Long.class).validate(this.application).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.applicationService.get()));
    }

    public Long getApplication() {
        return application;
    }

    public void setApplication(Long application) {
        this.application = application;
    }

    public static class References {

        @Inject private static Provider<BaseModelService<Application>> applicationService;

        private References() {
        }
    }

}
