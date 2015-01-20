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
import models.Cloud;
import models.Location;
import models.service.api.CloudServiceInterface;
import models.service.impl.LocationService;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class CloudLocationDto extends ValidatableDto {

    public static class References {

        @Inject
        public static Provider<CloudServiceInterface> cloudService;

        @Inject
        public static Provider<LocationService> locationService;
    }

    public Long cloud;
    public Long location;
    public String cloudUuid;

    public CloudLocationDto() {

    }

    public CloudLocationDto(Long cloud, Long location, String cloudUuid) {
        this.cloud = cloud;
        this.location = location;
        this.cloudUuid = cloudUuid;
    }

    @Override
    public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();

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

        //validate location reference
        Location location = null;
        if (this.location == null) {
            errors.add(new ValidationError("location", "The location is required."));
        } else {
            location = References.locationService.get().getById(this.location);
            if (location == null) {
                errors.add(new ValidationError("location", "The given location is invalid."));
            }
        }

        return errors;
    }
}
