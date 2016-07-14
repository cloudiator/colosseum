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

import models.Application;

import java.util.Collections;
import java.util.Set;

/**
 * Created by daniel on 14.07.16.
 */
public class AtLeastOnePublicPortProvidedWarning implements ModelValidator<Application> {

    @Override public Set<ValidationMessage> validate(Application application) {
        final boolean hasOnePublicPort = application.getApplicationComponents().stream()
            .flatMap(applicationComponent -> applicationComponent.getProvidedPorts().stream())
            .anyMatch(portProvided -> portProvided.getAttachedCommunications().isEmpty());
        if (hasOnePublicPort) {
            return Collections.emptySet();
        }
        return Collections.singleton(ValidationMessage.of(String
                .format("Application %s has no public provided port. Are you sure?", application),
            ValidationMessage.Type.WARNING));
    }

}
