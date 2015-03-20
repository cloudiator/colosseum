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

import com.google.common.reflect.TypeToken;
import dtos.validation.api.ValidationException;
import dtos.validation.generic.AbstractValidator;
import dtos.validation.generic.ValidationError;
import models.generic.Model;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 13.03.15.
 */
public class ModelIdValidator<T extends Model> extends AbstractValidator<Long> {

    private final ModelService<T> modelService;

    public ModelIdValidator(ModelService<T> modelService) {
        this.modelService = modelService;
    }

    @Override protected void validation(Long aLong) throws ValidationException {
        if (aLong == null) {
            throw new ValidationException("The model id must not be null.");
        }
        if (modelService.getById(aLong) == null) {
            this.addError(ValidationError
                .of(String.format("Could not find the Model with id %s.",aLong)));
        }
    }
}
