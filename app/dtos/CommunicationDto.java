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
import dtos.generic.ValidatableDto;
import dtos.validation.validators.ModelIdValidator;
import dtos.validation.validators.NotNullValidator;
import models.PortProvided;
import models.PortRequired;
import models.service.ModelService;

/**
 * Created by daniel on 09.01.15.
 */
public class CommunicationDto extends ValidatableDto {

    protected Long requiredPort;
    protected Long providedPort;

    public CommunicationDto() {
        super();
    }

    @Override public void validation() {
        validator(Long.class).validate(requiredPort).withValidator(new NotNullValidator())
            .withValidator(
                new ModelIdValidator<>(References.requiredPortModelServiceProvider.get()));
        validator(Long.class).validate(providedPort).withValidator(new NotNullValidator())
            .withValidator(
                new ModelIdValidator<>(References.providedPortModelServiceProvider.get()));

    }

    public Long getRequiredPort() {
        return requiredPort;
    }

    public void setRequiredPort(Long requiredPort) {
        this.requiredPort = requiredPort;
    }

    public Long getProvidedPort() {
        return providedPort;
    }

    public void setProvidedPort(Long providedPort) {
        this.providedPort = providedPort;
    }

    public static class References {

        @Inject private static Provider<ModelService<PortRequired>>
            requiredPortModelServiceProvider;
        @Inject private static Provider<ModelService<PortProvided>>
            providedPortModelServiceProvider;

        private References() {
        }
    }
}
