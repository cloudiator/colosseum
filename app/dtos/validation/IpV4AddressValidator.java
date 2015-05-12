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
import dtos.validation.api.Validator;
import dtos.validation.generic.ValidationError;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Checks if the given ipAddress is a valid ip address
 * with respect to ipV4.
 * <p>
 * Licensed under Apache 2.0 License, copyright for the regex by Google
 *
 * @see <a href="https://developers.google.com/web/fundamentals/input/form/provide-real-time-validation">Google WebFundamentals</a>
 */
public class IpV4AddressValidator implements Validator<String> {

    private final static String IPV4Regex =
        "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    @Override public Collection<ValidationError> validate(String s) throws ValidationException {
        if (s == null) {
            throw new ValidationException("Given ip address must not be null.");
        }
        Collection<ValidationError> validationErrors = new LinkedList<>();
        if (!s.matches(IPV4Regex)) {
            validationErrors
                .add(ValidationError.of(String.format("%s is not a valid ipv4 address", s)));
        }
        return validationErrors;
    }
}
