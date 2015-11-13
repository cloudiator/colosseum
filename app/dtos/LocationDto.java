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
import dtos.generic.RemoteDto;
import dtos.validation.validators.IterableValidator;
import dtos.validation.validators.ModelIdValidator;
import dtos.validation.validators.NotNullValidator;
import models.*;
import models.service.BaseModelService;
import models.service.ModelService;

import java.util.List;

/**
 * Created by daniel on 09.04.15.
 */
public class LocationDto extends RemoteDto {

    private Long cloud;
    private String name;
    private Long parent;
    private LocationScope locationScope;
    private Boolean isAssignable;
    private Long geoLocation;
    private List<Long> cloudCredentials;

    public LocationDto() {
        super();
    }


    @Override public void validation() {
        super.validation();
        validator(Long.class).validate(cloud).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.cloudService.get()));
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
        @Inject private static Provider<BaseModelService<Cloud>> cloudService;
        @Inject private static Provider<ModelService<GeoLocation>> geoLocationService;
        @Inject private static Provider<ModelService<Location>> locationService;
        @Inject private static Provider<ModelService<CloudCredential>> cloudCredentialService;
    }

    public Long getCloud() {
        return cloud;
    }

    public void setCloud(Long cloud) {
        this.cloud = cloud;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAssignable() {
        return isAssignable;
    }

    public void setAssignable(Boolean assignable) {
        isAssignable = assignable;
    }
}
