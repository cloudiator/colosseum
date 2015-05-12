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

package dtos.validation;

import dtos.validation.api.ValidationException;
import dtos.validation.generic.AbstractValidator;
import dtos.validation.generic.ValidationError;

/**
 * Created by daniel on 19.03.15.
 */
public class NotNullOrEmptyValidator extends AbstractValidator<String> {

    @Override protected void validation(String s) throws ValidationException {
        if (s == null || s.isEmpty()) {
            addError(ValidationError.of("The given value must not be empty."));
        }
    }
}
