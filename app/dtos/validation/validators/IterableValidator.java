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

package dtos.validation.validators;



import dtos.validation.AbstractValidator;
import dtos.validation.ValidationErrorMessage;
import dtos.validation.ValidationException;
import dtos.validation.Validator;

import java.util.Collection;

/**
 * Created by daniel on 19.03.15.
 */
public class IterableValidator<T> extends AbstractValidator<Iterable<T>> {

    private Validator<T> validator;

    public IterableValidator(Validator<T> tValidator) {
        this.validator = tValidator;
    }

    @Override protected void validation(Iterable<T> ts) throws ValidationException {

        if (ts == null) {
            throw new ValidationException("Iterable to validate is null.");
        }

        for (T t : ts) {
            Collection<ValidationErrorMessage> validatorErrors = this.validator.validate(t);
            validatorErrors.forEach(this::addError);
        }
    }
}
