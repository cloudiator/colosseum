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

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.impl.ValidatableDto;
import models.CloudApi;
import models.Credential;
import models.FrontendUser;
import models.service.api.CloudApiService;
import models.service.api.CredentialService;
import models.service.api.FrontendUserService;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class UserCredentialDto extends ValidatableDto {

    public static class References {
        @Inject
        public static Provider<CloudApiService> cloudApiService;

        @Inject
        public static Provider<CredentialService> credentialService;

        @Inject
        public static Provider<FrontendUserService> frontendUserService;
    }


    public Long cloudApi;

    public Long credential;

    public Long frontendUser;

    public UserCredentialDto(){

    }

    public UserCredentialDto(Long cloudApi, Long credential, Long frontendUser){

        this.cloudApi = cloudApi;
        this.credential = credential;
        this.frontendUser = frontendUser;
    }

    @Override
    public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();

        //validate cloudApi reference
        CloudApi cloudApi = null;
        if (this.cloudApi == null) {
            errors.add(new ValidationError("cloudApi", "The cloudApi is required."));
        } else {
            cloudApi = References.cloudApiService.get().getById(this.cloudApi);
            if (cloudApi == null) {
                errors.add(new ValidationError("cloudApi", "The given cloudApi is invalid."));
            }
        }

        //validate Credential reference
        Credential credential = null;
        if (this.credential == null) {
            errors.add(new ValidationError("credential", "The credential is required."));
        } else {
            credential = References.credentialService.get().getById(this.credential);
            if (credential == null) {
                errors.add(new ValidationError("credential", "The given credential is invalid."));
            }
        }

        //validate cloudApi reference
        FrontendUser frontendUser = null;
        if (this.frontendUser == null) {
            errors.add(new ValidationError("frontendUser", "The frontendUser is required."));
        } else {
            frontendUser = References.frontendUserService.get().getById(this.frontendUser);
            if (frontendUser == null) {
                errors.add(new ValidationError("frontendUser", "The given frontendUser is invalid."));
            }
        }

        return errors;
    }
}
