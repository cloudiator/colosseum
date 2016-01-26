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

package api.validation;

import com.google.common.collect.ImmutableList;
import com.google.inject.TypeLiteral;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 20.03.15.
 */
public class ValidationHolder implements ReferenceValidator {

    private Collection<ValidationBuilder> validationBuilders;
    private Iterable<ReferenceValidator> referenceValidators;
    private boolean isBuild = false;

    public ValidationHolder() {
        this.validationBuilders = new LinkedList<>();
    }

    private void buildOnce() {
        if (!isBuild) {
            build();
            isBuild = true;
        }
    }

    private void build() {
        final ImmutableList.Builder<ReferenceValidator> builder =
            ImmutableList.<ReferenceValidator>builder();
        for (ValidationBuilder validationBuilder : validationBuilders) {
            for (Object o : validationBuilder.build()) {
                builder.add((ReferenceValidator) o);
            }
        }
        this.referenceValidators = builder.build();
    }

    @Override public Collection<ValidationError> validate() throws ValidationException {
        buildOnce();
        List<ValidationError> validationErrors = new LinkedList<>();
        checkState(referenceValidators != null);
        for (ReferenceValidator referenceValidator : referenceValidators) {
            validationErrors.addAll(referenceValidator.validate());
        }
        return validationErrors;
    }

    public <T> ValidationBuilder<T> getBuilder(Class<T> clazz) {
        ValidationBuilder<T> validationBuilder = new ValidationBuilder<>();
        this.validationBuilders.add(validationBuilder);
        return validationBuilder;
    }

    public <T> ValidationBuilder<T> getBuilder(TypeLiteral<T> typeLiteral) {
        ValidationBuilder<T> validationBuilder = new ValidationBuilder<>();
        this.validationBuilders.add(validationBuilder);
        return validationBuilder;
    }



}
