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

package dtos.generic.impl;

import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * A Dto for named entities.
 */
public abstract class NamedDto extends ValidatableDto {

    private String name;

    /**
     * Default constructor, need for Play Forms API.
     */
    public NamedDto() {
        super();
    }

    /**
     * Constructor setting the name.
     *
     * @param name the name of the object.
     */
    public NamedDto(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();
        if (name == null || name.length() == 0) {
            errors.add(new ValidationError("name", "No name was given."));
        }
        return errors;
    }
}
