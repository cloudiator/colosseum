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
import dtos.validation.NotNullValidator;
import models.Api;
import models.Cloud;
import models.service.api.generic.ModelService;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class CloudApiDto extends ValidatableDto {


    protected Long api;
    protected Long cloud;
    protected String endpoint;

    public CloudApiDto() {

    }

    @Override public void validation() {
        validator(Long.class).validate(api).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.apiService.get()));
        validator(Long.class).validate(cloud).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.cloudService.get()));
        validator(String.class).validate(endpoint).withValidator(new NotNullOrEmptyValidator());
    }

    public CloudApiDto(Long api, Long cloud, String endpoint) {

        this.api = api;
        this.cloud = cloud;
        this.endpoint = endpoint;
    }

    public Long getApi() {
        return api;
    }

    public void setApi(Long api) {
        this.api = api;
    }

    public Long getCloud() {
        return cloud;
    }

    public void setCloud(Long cloud) {
        this.cloud = cloud;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public static class References {

        @Inject public static Provider<ModelService<Api>> apiService;

        @Inject public static Provider<ModelService<Cloud>> cloudService;
    }
}
