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
import dtos.generic.impl.NamedDto;
import models.OperatingSystem;
import models.service.api.OperatingSystemService;

import com.google.inject.Provider;
import play.data.validation.ValidationError;

import java.util.List;

public class ImageDto extends NamedDto {

    public static class References{

        @Inject
        public static Provider<OperatingSystemService> operatingSystemService;

    }

    protected Long operatingSystem;

    public ImageDto() {
        super();
    }

    public ImageDto(String name, Long operatingSystem) {
        super(name);
        this.operatingSystem = operatingSystem;
    }

    public Long getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(Long operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    @Override
    public List<ValidationError> validateNotNull() {
        final List<ValidationError> errors = super.validateNotNull();

        //validate cloud reference
        OperatingSystem operatingSystem = null;
        if (this.operatingSystem == null) {
            errors.add(new ValidationError("operatingsystem", "The operatingsystem is required."));
        } else {
            operatingSystem = References.operatingSystemService.get().getById(this.operatingSystem);
            if (operatingSystem == null) {
                errors.add(new ValidationError("operatingsystem", "The given operatingsystem is invalid."));
            }
        }

        return errors;
    }
}
