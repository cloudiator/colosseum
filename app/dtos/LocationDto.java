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
import models.Cloud;
import models.LocationProperties;
import models.service.impl.generic.BaseModelService;

/**
 * Created by daniel on 09.04.15.
 */
public class LocationDto extends ValidatableDto {
    protected Long cloud;
    protected Long locationProperties;
    protected String cloudUuid;

    public LocationDto() {
        super();
    }

    public LocationDto(Long cloud, Long locationProperties, String cloudUuid) {
        this.cloud = cloud;
        this.locationProperties = locationProperties;
        this.cloudUuid = cloudUuid;
    }

    @Override public void validation() {
        validator(Long.class).validate(cloud).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.cloudService.get()));
        validator(Long.class).validate(locationProperties).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.locationPropertiesService.get()));
        validator(String.class).validate(cloudUuid).withValidator(new NotNullOrEmptyValidator());
    }

    public static class References {
        @Inject public static Provider<BaseModelService<Cloud>> cloudService;
        @Inject public static Provider<BaseModelService<LocationProperties>>
            locationPropertiesService;
    }
}
