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
import dtos.validation.validators.ModelIdValidator;
import dtos.validation.validators.NotNullOrEmptyValidator;
import dtos.validation.validators.NotNullValidator;
import models.OperatingSystemArchitecture;
import models.OperatingSystemVendor;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 15.04.15.
 */
public class OperatingSystemDto extends ValidatableDto {

    private OperatingSystemArchitecture operatingSystemArchitecture;
    private Long operatingSystemVendor;
    private String version;

    @Override public void validation() {
        validator(OperatingSystemArchitecture.class).validate(operatingSystemArchitecture)
            .withValidator(new NotNullValidator());
        validator(Long.class).validate(operatingSystemVendor)
            .withValidator(new ModelIdValidator<>(References.operatingSystemVendorValidator.get()));
        validator(String.class).validate(version).withValidator(new NotNullOrEmptyValidator());
    }

    public OperatingSystemArchitecture getOperatingSystemArchitecture() {
        return operatingSystemArchitecture;
    }

    public void setOperatingSystemArchitecture(
        OperatingSystemArchitecture operatingSystemArchitecture) {
        this.operatingSystemArchitecture = operatingSystemArchitecture;
    }

    public Long getOperatingSystemVendor() {
        return operatingSystemVendor;
    }

    public void setOperatingSystemVendor(Long operatingSystemVendor) {
        this.operatingSystemVendor = operatingSystemVendor;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static class References {

        @Inject private static Provider<ModelService<OperatingSystemVendor>>
            operatingSystemVendorValidator;

        private References() {
        }
    }
}
