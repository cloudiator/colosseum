/*
 * Copyright (c) 2014-2016 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package api.dto;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import api.validation.validators.IterableValidator;
import api.validation.validators.ModelIdValidator;
import api.validation.validators.NotNullValidator;
import models.Cloud;
import models.CloudCredential;
import models.Location;
import models.OperatingSystem;
import models.service.ModelService;

import java.util.List;

public class ImageDto extends RemoteDto {

    private String name;
    private Long cloud;
    private Long location;
    private Long operatingSystem;
    private List<Long> cloudCredentials;
    private String defaultLoginUsername;
    private String defaultLoginPassword;

    public ImageDto() {
        super();
    }

    @Override public void validation() {
        super.validation();
        validator(Long.class).validate(cloud).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.cloudService.get()));
        validator(Long.class).validate(location)
            .withValidator(new ModelIdValidator<>(References.locationService.get()));
        validator(new TypeLiteral<List<Long>>() {
        }).validate(cloudCredentials).withValidator(new IterableValidator<>(
            new ModelIdValidator<>(References.cloudCredentialService.get())));
        validator(Long.class).validate(operatingSystem)
            .withValidator(new ModelIdValidator<>(References.operatingSystemService.get()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long location) {
        this.location = location;
    }

    public List<Long> getCloudCredentials() {
        return cloudCredentials;
    }

    public void setCloudCredentials(List<Long> cloudCredentials) {
        this.cloudCredentials = cloudCredentials;
    }

    public String getDefaultLoginUsername() {
        return defaultLoginUsername;
    }

    public void setDefaultLoginUsername(String defaultLoginUsername) {
        this.defaultLoginUsername = defaultLoginUsername;
    }

    public String getDefaultLoginPassword() {
        return defaultLoginPassword;
    }

    public void setDefaultLoginPassword(String defaultLoginPassword) {
        this.defaultLoginPassword = defaultLoginPassword;
    }

    public static class References {

        @Inject private static Provider<ModelService<Cloud>> cloudService;
        @Inject private static Provider<ModelService<Location>> locationService;
        @Inject private static Provider<ModelService<OperatingSystem>> operatingSystemService;
        @Inject private static Provider<ModelService<CloudCredential>> cloudCredentialService;

        private References() {
        }
    }
}
