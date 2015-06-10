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
import dtos.validation.validators.NotNullOrEmptyValidator;
import models.OperatingSystem;
import models.service.impl.generic.BaseModelService;

public class ImageOfferDto extends ValidatableDto {

    private String name;
    private Long operatingSystem;

    public ImageOfferDto() {
        super();
    }

    public ImageOfferDto(String name, Long operatingSystem) {
        this.name = name;
        this.operatingSystem = operatingSystem;
    }

    /**
     * @todo validate operating system.
     */
    @Override public void validation() {
        validator(String.class).validate(this.name).withValidator(new NotNullOrEmptyValidator());
    }

    public Long getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(Long operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class References {
        @Inject public static Provider<BaseModelService<OperatingSystem>> operatingSystemService;
    }
}
