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

import dtos.generic.ValidatableDto;
import dtos.validation.validators.NotNullOrEmptyValidator;
import dtos.validation.validators.NotNullValidator;
import models.OperatingSystemVendorType;

import javax.annotation.Nullable;

/**
 * Created by daniel on 15.04.15.
 * @todo add changes to api documentation
 */
public class OperatingSystemVendorDto extends ValidatableDto {

    private String name;
    private OperatingSystemVendorType operatingSystemVendorType;
    @Nullable private String defaultUserName;
    @Nullable private String defaultPassword;

    @Override public void validation() {
        validator(String.class).validate(name).withValidator(new NotNullOrEmptyValidator());
        validator(OperatingSystemVendorType.class).validate(operatingSystemVendorType)
            .withValidator(new NotNullValidator());
        //todo add validation for username and password
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OperatingSystemVendorType getOperatingSystemVendorType() {
        return operatingSystemVendorType;
    }

    public void setOperatingSystemVendorType(OperatingSystemVendorType operatingSystemVendorType) {
        this.operatingSystemVendorType = operatingSystemVendorType;
    }

    @Nullable public String getDefaultUserName() {
        return defaultUserName;
    }

    public void setDefaultUserName(@Nullable String defaultUserName) {
        this.defaultUserName = defaultUserName;
    }

    @Nullable public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(@Nullable String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }
}
