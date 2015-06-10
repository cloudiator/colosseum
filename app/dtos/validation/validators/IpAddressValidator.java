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


import dtos.validation.ValidationErrorMessage;
import dtos.validation.ValidationException;
import dtos.validation.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class IpAddressValidator implements Validator<String> {

    private final List<Validator<String>> ipValidators;

    public IpAddressValidator() {
        this.ipValidators = new ArrayList<>(2);
        ipValidators.add(new IpV4AddressValidator());
        ipValidators.add(new IpV6AddressValidator());
    }

    @Override public Collection<ValidationErrorMessage> validate(String s)
        throws ValidationException {
        if (s == null) {
            throw new ValidationException("Given ip address must not be null.");
        }

        boolean isValid = false;
        for (Validator<String> ipValidator : ipValidators) {
            if (ipValidator.validate(s).isEmpty()) {
                isValid = true;
            }
        }

        Collection<ValidationErrorMessage> validationErrorMessages = new LinkedList<>();
        if (!isValid) {
            validationErrorMessages
                .add(ValidationErrorMessage.of(String.format("%s is not a valid ip address", s)));
        }
        return validationErrorMessages;
    }
}
