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
import dtos.generic.NeedsValidationDto;
import models.Api;
import models.Cloud;
import models.service.impl.generic.BaseModelService;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class CloudApiDto extends NeedsValidationDto {


    protected Long api;
    protected Long cloud;
    protected String endpoint;

    public CloudApiDto() {

    }

    @Override public void validation() {

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

//    @Override public List<ValidationError> validateNotNull() {
//        List<ValidationError> errors = new ArrayList<>();
//
//        //validate api reference
//        Api api = null;
//        if (this.api == null) {
//            errors.add(new ValidationError("api", "The api is required."));
//        } else {
//            api = References.apiService.get().getById(this.api);
//            if (api == null) {
//                errors.add(new ValidationError("api", "The given api is invalid."));
//            }
//        }
//
//        //validate cloud reference
//        Cloud cloud = null;
//        if (this.cloud == null) {
//            errors.add(new ValidationError("cloud", "The cloud is required."));
//        } else {
//            cloud = References.cloudService.get().getById(this.cloud);
//            if (cloud == null) {
//                errors.add(new ValidationError("cloud", "The given cloud is invalid."));
//            }
//        }
//
//
//        return errors;
//    }


    public static class References {

        @Inject public static Provider<BaseModelService<Api>> apiService;

        @Inject public static Provider<BaseModelService<Cloud>> cloudService;
    }
}
