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
import dtos.validation.validators.NotNullOrEmptyValidator;
import dtos.validation.validators.NotNullValidator;
import models.ApplicationComponent;
import models.service.ModelService;

/**
 * Created by daniel on 03.08.15.
 */
public class PortDto extends ValidatableDto {

    private String name;
    private Long applicationComponent;

    public PortDto() {
    }

    @Override public void validation() {
        validator(String.class).validate(name).withValidator(new NotNullOrEmptyValidator());
        validator(Long.class).validate(applicationComponent).withValidator(new NotNullValidator())
            .withValidator(
                new ModelIdValidator<>(References.applicationComponentServiceProvider.get()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getApplicationComponent() {
        return applicationComponent;
    }

    public void setApplicationComponent(Long applicationComponent) {
        this.applicationComponent = applicationComponent;
    }

    public static final class References {

        @Inject private static Provider<ModelService<ApplicationComponent>>
            applicationComponentServiceProvider;

        private References() {
        }
    }

}
