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

import api.validation.validators.NotNullOrEmptyValidator;

/**
 * Created by daniel seybold on 16.12.2014.
 */
public class ApplicationDto extends ValidatableDto {

    private String name;

    public ApplicationDto() {
        super();
    }

    public ApplicationDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override public void validation() {
        validator(String.class).validate(this.name).withValidator(new NotNullOrEmptyValidator());
    }
}
