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
import models.Cloud;
import models.LocationProperties;
import models.service.impl.generic.BaseModelService;

/**
 * Created by daniel on 09.04.15.
 */
public class LocationDto extends ValidatableDto {
    @Override public void validation() {
        
    }

    protected Long cloud;
    protected Long location;
    protected String cloudUuid;

    public LocationDto() {
        super();
    }

    public LocationDto(Long cloud, Long location, String cloudUuid) {
        this.cloud = cloud;
        this.location = location;
        this.cloudUuid = cloudUuid;
    }

    public Long getCloud() {
        return cloud;
    }

    public void setCloud(Long cloud) {
        this.cloud = cloud;
    }

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long location) {
        this.location = location;
    }

    public String getCloudUuid() {
        return cloudUuid;
    }

    public void setCloudUuid(String cloudUuid) {
        this.cloudUuid = cloudUuid;
    }

    //    @Override public List<ValidationError> validateNotNull() {
    //        List<ValidationError> errors = new ArrayList<>();
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
    //        //validate location reference
    //        LocationProperties locationProperties = null;
    //        if (this.location == null) {
    //            errors.add(new ValidationError("location", "The location is required."));
    //        } else {
    //            locationProperties = References.locationPropertiesService.get().getById(this.location);
    //            if (locationProperties == null) {
    //                errors.add(new ValidationError("location", "The given location is invalid."));
    //            }
    //        }
    //
    //        return errors;
    //    }


    public static class References {

        @Inject public static Provider<BaseModelService<Cloud>> cloudService;

        @Inject public static Provider<BaseModelService<LocationProperties>> locationPropertiesService;
    }
}
