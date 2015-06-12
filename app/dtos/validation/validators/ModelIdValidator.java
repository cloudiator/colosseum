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
import models.generic.Model;
import models.service.api.generic.ModelService;

import javax.annotation.Nullable;

/**
 * Created by daniel on 13.03.15.
 */
public class ModelIdValidator<T extends Model> extends AbstractValidator<Long> {

    private final ModelService<T> modelService;

    public ModelIdValidator(ModelService<T> modelService) {
        this.modelService = modelService;
    }

    @Override protected void validation(@Nullable Long id) throws ValidationException {
        if (id == null) {
            return;
        }
        if (modelService.getById(id) == null) {
            this.addError(ValidationErrorMessage
                .of(String.format("Could not find the Model with id %s.", id)));
        }
    }
}
