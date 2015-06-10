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
import dtos.validation.ModelIdValidator;
import dtos.validation.NotNullOrEmptyValidator;
import models.Api;
import models.service.api.generic.ModelService;

public class CloudDto extends ValidatableDto {

    private String name;
    private String endpoint;
    private Long api;

    public CloudDto() {
        super();
    }

    public CloudDto(String name, String endpoint, Long api) {
        this.name = name;
        this.endpoint = endpoint;
        this.api = api;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Long getApi() {
        return api;
    }

    public void setApi(Long api) {
        this.api = api;
    }

    @Override public void validation() {
        validator(String.class).validate(this.name).withValidator(new NotNullOrEmptyValidator());
        validator(String.class).validate(this.endpoint)
            .withValidator(new NotNullOrEmptyValidator());
        validator(Long.class).validate(this.api)
            .withValidator(new ModelIdValidator<>(References.apiService.get()));
    }

    public static class References {
        @Inject private static Provider<ModelService<Api>> apiService;
    }
}
