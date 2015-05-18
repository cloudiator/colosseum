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

package dtos.api;

import play.data.validation.ValidationError;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by bwpc on 08.12.2014.
 */
public interface Validatable {

    /**
     * Provides a function to validate the object.
     * <p>
     * Used by the play framework forms.
     *
     * @return null if no errors are present, otherwise a list of validation errors.
     */
    @Nullable public List<ValidationError> validate();

}
