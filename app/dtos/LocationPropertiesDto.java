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
import dtos.generic.NamedDto;
import dtos.validation.ModelIdValidator;
import dtos.validation.NotNullValidator;
import models.GeoLocation;
import models.LocationProperties;
import models.LocationScope;
import models.service.api.generic.ModelService;

public class LocationPropertiesDto extends NamedDto {

    private Long parent;
    private LocationScope locationScope;
    private Boolean isAssignable;
    private Long geoLocation;

    public LocationPropertiesDto() {
    }

    public LocationPropertiesDto(String name, Long parent, LocationScope locationScope,
        Boolean isAssignable, Long geoLocation) {
        super(name);
        this.parent = parent;
        this.locationScope = locationScope;
        this.isAssignable = isAssignable;
        this.geoLocation = geoLocation;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public LocationScope getLocationScope() {
        return locationScope;
    }

    public void setLocationScope(LocationScope locationScope) {
        this.locationScope = locationScope;
    }

    public Boolean isAssignable() {
        return isAssignable;
    }

    public void setIsAssignable(Boolean isAssignable) {
        this.isAssignable = isAssignable;
    }

    public Long getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(Long geoLocation) {
        this.geoLocation = geoLocation;
    }

    @Override public void validation() {
        super.validation();
        validator(Long.class).validate(parent).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.locationPropertiesService.get()));
        validator(Long.class).validate(geoLocation).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.geoLocationService.get()));
        validator(Boolean.class).validate(isAssignable).withValidator(new NotNullValidator());
        validator(LocationScope.class).validate(locationScope)
            .withValidator(new NotNullValidator());
    }

    public static class References {
        @Inject public static Provider<ModelService<LocationProperties>> locationPropertiesService;
        @Inject public static Provider<ModelService<GeoLocation>> geoLocationService;
    }
}
