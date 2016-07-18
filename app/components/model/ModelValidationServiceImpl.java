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

package components.model;

import com.google.inject.Inject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import models.Application;
import play.Logger;
import util.logging.Loggers;

/**
 * Created by daniel on 14.07.16.
 */
public class ModelValidationServiceImpl implements ModelValidationService {

    private final static Logger.ALogger LOGGER = Loggers.of(Loggers.MODEL);

    private final Set<ModelValidator<Application>> modelValidators;

    @Inject public ModelValidationServiceImpl(Set<ModelValidator<Application>> modelValidators) {
        this.modelValidators = modelValidators;
    }

    @Override public void validate(Application application) throws ModelValidationException {
        final Set<ValidationMessage> collect = modelValidators.stream().flatMap(
            applicationModelValidator -> applicationModelValidator.validate(application).stream())
            .collect(Collectors.toSet());
        logWarningsAndThrowErrors(collect);
    }

    private void logWarningsAndThrowErrors(Set<ValidationMessage> validationMessages)
        throws ModelValidationException {
        Set<ValidationMessage> errors = new HashSet<>(validationMessages.size());
        Set<ValidationMessage> warnings = new HashSet<>(validationMessages.size());
        validationMessages.forEach(validationMessage -> {
            switch (validationMessage.type()) {
                case ERROR:
                    errors.add(validationMessage);
                    break;
                case WARNING:
                    warnings.add(validationMessage);
                    break;
            }
        });
        warnings.forEach(validationMessage -> LOGGER.warn(validationMessage.message()));
        if (!errors.isEmpty()) {
            throw new ModelValidationException(buildErrorMessage(errors));
        }
    }

    private String buildErrorMessage(Set<ValidationMessage> validationMessages) {
        return String.format("%s errors have been found while validating the application: %s",
            validationMessages.size(), Arrays.toString(validationMessages.toArray()));
    }
}
