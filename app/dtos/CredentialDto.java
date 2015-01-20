/*
 * Copyright (c) 2015 University of Ulm
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
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class CredentialDto extends ValidatableDto {

    public String user;
    public String secret;

    public CredentialDto() {

    }

    public CredentialDto(String user, String secret) {
        this.user = user;
        this.secret = secret;
    }

    @Override
    public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();

        if(this.user.isEmpty()){
            errors.add(new ValidationError("credentials", "User must not be empty"));
        }

        if(this.secret.isEmpty()){
            errors.add(new ValidationError("credentials", "Secret must not be empty"));
        }

        return errors;
    }
}
