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

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 20.03.15.
 */
public class ValidationError {

    private static final String DEFAULT_FIELD = "general";

    private final String message;
    private final String field;

    private ValidationError(@Nullable String field, String message) {

        if (field == null) {
            this.field = DEFAULT_FIELD;
        } else {
            checkArgument(!field.isEmpty());
            this.field = field;
        }
        this.message = message;
    }

    public static ValidationError of(@Nullable String field,
        ValidationErrorMessage validationErrorMessage) {
        return new ValidationError(field, validationErrorMessage.getMessage());
    }

    public static ValidationError of(ValidationErrorMessage validationErrorMessage) {
        return new ValidationError(null, validationErrorMessage.getMessage());
    }

    public play.data.validation.ValidationError toPlayError() {
        return new play.data.validation.ValidationError(field, message);
    }
}
