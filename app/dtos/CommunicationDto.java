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
import dtos.validation.validators.ExpressionValidator;
import dtos.validation.validators.ModelIdValidator;
import dtos.validation.validators.NotNullValidator;
import models.ApplicationComponent;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 09.01.15.
 */
public class CommunicationDto extends ValidatableDto {

    protected Long provider;
    protected Long consumer;
    protected Integer port;

    public CommunicationDto(Long provider, Long consumer, Integer port) {
        this.provider = provider;
        this.consumer = consumer;
        this.port = port;
    }

    public CommunicationDto() {
        super();
    }

    @Override public void validation() {
        validator(Long.class).validate(provider).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.applicationComponentService.get()));
        validator(Long.class).validate(consumer).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.applicationComponentService.get()));
        validator(Integer.class).validate(port).withValidator(new NotNullValidator())
            .withValidator(new ExpressionValidator(port.compareTo(0) > 0));
    }

    public Long getProvider() {
        return provider;
    }

    public void setProvider(Long provider) {
        this.provider = provider;
    }

    public Long getConsumer() {
        return consumer;
    }

    public void setConsumer(Long consumer) {
        this.consumer = consumer;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public static class References {
        @Inject public static Provider<ModelService<ApplicationComponent>>
            applicationComponentService;
    }
}
