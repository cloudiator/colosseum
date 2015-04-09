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

package dtos.generic;

import dtos.api.Dto;
import dtos.api.Validatable;
import dtos.validation.api.ValidationException;
import dtos.validation.generic.ValidationBuilder;
import dtos.validation.generic.ValidationHolder;
import play.data.validation.ValidationError;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by daniel on 08.12.14.
 */
public abstract class ValidatableDto implements Dto, Validatable {

    private ValidationHolder validationHolder;

    public ValidatableDto() {
        super();
        this.validationHolder = new ValidationHolder();
    }

    public <S> ValidationBuilder<S> validator(Class<S> sClass) {
        return this.validationHolder.getBuilder(sClass);
    }

    public abstract void validation();


    @Nullable @Override public List<ValidationError> validate() {
        validation();
        List<ValidationError> errors = new LinkedList<>();
        try {
            errors.addAll(validationHolder.validate().stream()
                .map(dtos.validation.generic.ValidationError::toPlayError)
                .collect(Collectors.toList()));
        } catch (ValidationException e) {
            if (errors.isEmpty()) {
                throw new RuntimeException(
                    "Illegal state in validation. An validation exception occurred but the error list is empty.",
                    e);
            }
        }
        if (errors.isEmpty()) {
            return null;
        }
        return errors;
    }

}
