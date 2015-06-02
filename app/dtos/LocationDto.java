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
import com.google.inject.TypeLiteral;
import dtos.generic.ValidatableDto;
import dtos.validation.IterableValidator;
import dtos.validation.ModelIdValidator;
import dtos.validation.NotNullOrEmptyValidator;
import dtos.validation.NotNullValidator;
import models.*;
import models.service.api.generic.ModelService;
import models.service.impl.generic.BaseModelService;

import java.util.List;

/**
 * Created by daniel on 09.04.15.
 */
public class LocationDto extends ValidatableDto {

    protected Long cloud;
    protected String cloudUuid;
    private Long parent;
    private LocationScope locationScope;
    private Boolean isAssignable;
    private Long geoLocation;
    private List<Long> cloudCredentials;

    public LocationDto() {
        super();
    }

    public LocationDto(Long cloud, String cloudUuid, Long parent, LocationScope locationScope,
        Boolean isAssignable, Long geoLocation) {
        this.cloud = cloud;
        this.cloudUuid = cloudUuid;
        this.parent = parent;
        this.locationScope = locationScope;
        this.isAssignable = isAssignable;
        this.geoLocation = geoLocation;
    }

    @Override public void validation() {
        validator(Long.class).validate(cloud).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.cloudService.get()));
        validator(String.class).validate(cloudUuid).withValidator(new NotNullOrEmptyValidator());
        validator(Long.class).validate(geoLocation).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.geoLocationService.get()));
        validator(Boolean.class).validate(isAssignable).withValidator(new NotNullValidator());
        validator(LocationScope.class).validate(locationScope)
            .withValidator(new NotNullValidator());
        validator(Long.class).validate(parent)
            .withValidator(new ModelIdValidator<>(References.locationService.get()));
        validator(new TypeLiteral<List<Long>>() {
        }).validate(cloudCredentials).withValidator(new IterableValidator<>(
            new ModelIdValidator<>(References.cloudCredentialService.get())));
    }

    public static class References {
        @Inject public static Provider<BaseModelService<Cloud>> cloudService;
        @Inject public static Provider<ModelService<GeoLocation>> geoLocationService;
        @Inject public static Provider<ModelService<Location>> locationService;
        @Inject public static Provider<ModelService<CloudCredential>> cloudCredentialService;
    }

    public Long getCloud() {
        return cloud;
    }

    public void setCloud(Long cloud) {
        this.cloud = cloud;
    }

    public String getCloudUuid() {
        return cloudUuid;
    }

    public void setCloudUuid(String cloudUuid) {
        this.cloudUuid = cloudUuid;
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

    public Boolean getIsAssignable() {
        return isAssignable;
    }

    public List<Long> getCloudCredentials() {
        return cloudCredentials;
    }

    public void setCloudCredentials(List<Long> cloudCredentials) {
        this.cloudCredentials = cloudCredentials;
    }
}
