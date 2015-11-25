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
import dtos.validation.validators.NotNullOrEmptyValidator;
import dtos.validation.validators.NotNullValidator;
import models.Cloud;
import models.CloudCredential;
import models.HardwareOffer;
import models.Location;
import models.service.BaseModelService;
import models.service.ModelService;

import java.util.List;

public class HardwareDto extends RemoteDto {

    private String name;
    private Long cloud;
    private Long hardwareOffer;
    private Long location;
    private List<Long> cloudCredentials;

    public HardwareDto() {
        super();
    }

    @Override public void validation() {
        super.validation();
        validator(String.class).validate(name).withValidator(new NotNullOrEmptyValidator());
        validator(Long.class).validate(cloud).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.cloudService.get()));
        validator(Long.class).validate(hardwareOffer).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.hardwareOfferService.get()));
        validator(Long.class).validate(location)
            .withValidator(new ModelIdValidator<>(References.locationService.get()));
        validator(new TypeLiteral<List<Long>>() {
        }).validate(cloudCredentials).withValidator(new IterableValidator<>(
            new ModelIdValidator<>(References.cloudCredentialService.get())));
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

    public Long getHardwareOffer() {
        return hardwareOffer;
    }

    public void setHardwareOffer(Long hardwareOffer) {
        this.hardwareOffer = hardwareOffer;
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

    public static class References {

        @Inject private static Provider<BaseModelService<Cloud>> cloudService;
        @Inject private static Provider<BaseModelService<HardwareOffer>> hardwareOfferService;
        @Inject private static Provider<ModelService<Location>> locationService;
        @Inject private static Provider<ModelService<CloudCredential>> cloudCredentialService;

        private References() {
        }
    }
}
