/*
 * Copyright (c) 2014-2015 University of Ulm
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

package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;

import dtos.generic.ValidatableDto;
import dtos.validation.validators.ModelIdValidator;
import dtos.validation.validators.NotNullOrEmptyValidator;
import dtos.validation.validators.NotNullValidator;
import de.uniulm.omi.cloudiator.persistance.entities.Cloud;
import de.uniulm.omi.cloudiator.persistance.repositories.ModelService;

/**
 * Created by daniel on 08.09.15.
 *
 * @todo add to documentation
 */
public class CloudPropertyDto extends ValidatableDto {

    private String key;
    private String value;
    private Long cloud;

    @Override public void validation() {
        validator(String.class).validate(key).withValidator(new NotNullOrEmptyValidator());
        validator(String.class).validate(value).withValidator(new NotNullOrEmptyValidator());
        validator(Long.class).validate(cloud).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.cloudService.get()));
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setCloud(Long cloud) {
        this.cloud = cloud;
    }

    public Long getCloud() {
        return cloud;
    }

    public static class References {
        @Inject private static Provider<ModelService<Cloud>> cloudService;

        private References() {
        }
    }
}
