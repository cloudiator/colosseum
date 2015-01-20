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
import dtos.generic.impl.ValidatableDto;
import models.Api;
import models.Cloud;
import models.service.api.ApiServiceInterface;
import models.service.api.CloudServiceInterface;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class CloudApiDto extends ValidatableDto {


    public static class References{

        @Inject
        public static Provider<ApiServiceInterface> apiService;

        @Inject
        public static Provider<CloudServiceInterface> cloudService;
    }

    public Long api;

    public Long cloud;

    public String endpoint;

    public CloudApiDto(){

    }

    public CloudApiDto(Long api, Long cloud, String endpoint){

        this.api = api;
        this.cloud = cloud;
        this.endpoint = endpoint;
    }

    @Override
    public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();

        //validate api reference
        Api api = null;
        if (this.api == null) {
            errors.add(new ValidationError("api", "The api is required."));
        } else {
            api = References.apiService.get().getById(this.api);
            if (api == null) {
                errors.add(new ValidationError("api", "The given api is invalid."));
            }
        }

        //validate cloud reference
        Cloud cloud = null;
        if (this.cloud == null) {
            errors.add(new ValidationError("cloud", "The cloud is required."));
        } else {
            cloud = References.cloudService.get().getById(this.cloud);
            if (cloud == null) {
                errors.add(new ValidationError("cloud", "The given cloud is invalid."));
            }
        }


        return errors;
    }
}
