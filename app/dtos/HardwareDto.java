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
import models.Cloud;
import models.CloudCredential;
import models.HardwareOffer;
import models.Location;
import models.service.api.generic.ModelService;
import models.service.impl.generic.BaseModelService;

import java.util.List;

public class HardwareDto extends ValidatableDto {

    private Long cloud;
    private Long hardwareOffer;
    private String cloudUuid;
    private List<Long> locations;
    private List<Long> cloudCredentials;

    public HardwareDto() {
        super();
    }

    @Override public void validation() {
        validator(Long.class).validate(cloud).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.cloudService.get()));
        validator(Long.class).validate(hardwareOffer).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.hardwareOfferService.get()));
        validator(String.class).validate(cloudUuid).withValidator(new NotNullOrEmptyValidator());
        validator(new TypeLiteral<List<Long>>() {
        }).validate(locations).withValidator(
            new IterableValidator<>(new ModelIdValidator<>(References.locationService.get())));
        validator(new TypeLiteral<List<Long>>() {
        }).validate(cloudCredentials).withValidator(new IterableValidator<>(
            new ModelIdValidator<>(References.cloudCredentialService.get())));
    }

    public Long getCloud() {
        return cloud;
    }

    public void setCloud(Long cloud) {
        this.cloud = cloud;
    }

    public Long getHardwareOffer() {
        return hardwareOffer;
    }

    public void setHardwareOffer(Long hardwareOffer) {
        this.hardwareOffer = hardwareOffer;
    }

    public String getCloudUuid() {
        return cloudUuid;
    }

    public void setCloudUuid(String cloudUuid) {
        this.cloudUuid = cloudUuid;
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

        @Inject private static Provider<BaseModelService<Cloud>> cloudService;
        @Inject private static Provider<BaseModelService<HardwareOffer>> hardwareOfferService;
        @Inject private static Provider<ModelService<Location>> locationService;
        @Inject private static Provider<ModelService<CloudCredential>> cloudCredentialService;

        private References() {
        }
    }
}
