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

package dtos;

import dtos.generic.impl.ValidatableDto;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class FrontendUserDto extends ValidatableDto {

    public String firstName;
    public String lastName;
    public String mail;

    public FrontendUserDto() {

    }

    public FrontendUserDto(String firstName, String lastName, String mail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
    }

    @Override
    public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();

        if(this.firstName.isEmpty()){
            errors.add(new ValidationError("frontenduser", "Firstname must not be empty"));
        }

        if(this.lastName.isEmpty()){
            errors.add(new ValidationError("frontenduser", "Lastname must not be empty"));
        }

        if(!Constraints.email().isValid(this.mail)){
            errors.add(new ValidationError("frontenduser", "Mail is not valid"));
        }

        return errors;
    }
}
