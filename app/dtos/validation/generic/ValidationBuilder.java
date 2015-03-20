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

package dtos.validation.generic;

import dtos.validation.api.ReferenceValidator;
import dtos.validation.api.ValidateBuilder;
import dtos.validation.api.Validator;
import dtos.validation.api.ValidatorBuilder;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by daniel on 20.03.15.
 */
public class ValidationBuilder<S> implements ValidateBuilder, ValidatorBuilder<S> {

    private Class<S> type;
    @Nullable private S s;
    private List<Validator<? super S>> validators = new LinkedList<>();

    public ValidationBuilder(Class<S> clazz) {
        this.type = clazz;
    }

    @Override public <T> ValidatorBuilder<T> validate(@Nullable T t) {
        //noinspection unchecked
        this.s = (S) t;
        //noinspection unchecked
        return (ValidatorBuilder<T>) this;
    }

    @Override public ValidatorBuilder<S> withValidator(Validator<? super S> validator) {
        this.validators.add(validator);
        return this;
    }

    public List<ReferenceValidator> build() {
        List<ReferenceValidator> referenceValidators = new ArrayList<>(validators.size());
        for (Validator<? super S> validator : validators) {
            referenceValidators.add(new GenericReferenceValidator<>(s, validator));
        }
        return referenceValidators;
    }
}
