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
import dtos.validation.validators.IterableValidator;
import dtos.validation.validators.ModelIdValidator;
import dtos.validation.validators.NotNullOrEmptyValidator;
import dtos.validation.validators.NotNullValidator;
import models.Cloud;
import models.CloudCredential;
import models.Location;
import models.OperatingSystem;
import models.service.api.generic.ModelService;

import java.util.List;

public class ImageDto extends ValidatableDto {

    private String name;
    private String cloudUuid;
    private Long cloud;
    private List<Long> locations;
    private Long operatingSystem;
    private List<Long> cloudCredentials;

    public ImageDto() {
        super();
    }

    @Override public void validation() {
        validator(String.class).validate(cloudUuid).withValidator(new NotNullOrEmptyValidator());
        validator(Long.class).validate(cloud).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.cloudService.get()));
        validator(new TypeLiteral<List<Long>>() {
        }).validate(locations).withValidator(
            new IterableValidator<>(new ModelIdValidator<>(References.locationService.get())));
        validator(new TypeLiteral<List<Long>>() {
        }).validate(cloudCredentials).withValidator(new IterableValidator<>(
            new ModelIdValidator<>(References.cloudCredentialService.get())));
        validator(Long.class).validate(operatingSystem).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.operatingSystemService.get()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCloudUuid() {
        return cloudUuid;
    }

    public void setCloudUuid(String cloudUuid) {
        this.cloudUuid = cloudUuid;
    }

    public Long getCloud() {
        return cloud;
    }

    public void setCloud(Long cloud) {
        this.cloud = cloud;
    }

    public Long getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(Long operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public List<Long> getLocations() {
        return locations;
    }

    public void setLocations(List<Long> locations) {
        this.locations = locations;
    }

    public List<Long> getCloudCredentials() {
        return cloudCredentials;
    }

    public void setCloudCredentials(List<Long> cloudCredentials) {
        this.cloudCredentials = cloudCredentials;
    }

    public static class References {
        @Inject public static Provider<ModelService<Cloud>> cloudService;
        @Inject public static Provider<ModelService<Location>> locationService;
        @Inject public static Provider<ModelService<OperatingSystem>> operatingSystemService;
        @Inject public static Provider<ModelService<CloudCredential>> cloudCredentialService;
    }
}
