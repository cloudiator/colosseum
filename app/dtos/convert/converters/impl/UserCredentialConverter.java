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

package dtos.convert.converters.impl;

import com.google.inject.Inject;
import dtos.UserCredentialDto;
import dtos.builders.UserCredentialDtoBuilder;
import models.CloudApi;
import models.Credential;
import models.FrontendUser;
import models.UserCredential;
import models.service.impl.CloudApiService;
import models.service.impl.CredentialService;
import models.service.impl.FrontendUserService;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class UserCredentialConverter extends BaseConverter<UserCredential, UserCredentialDto> {

    private final CloudApiService cloudApiService;
    private final CredentialService credentialService;
    private final FrontendUserService frontendUserService;

    @Inject
    UserCredentialConverter(CloudApiService cloudApiService, CredentialService credentialService, FrontendUserService frontendUserService) {

        checkNotNull(cloudApiService);
        checkNotNull(credentialService);
        checkNotNull(frontendUserService);

        this.cloudApiService = cloudApiService;
        this.credentialService = credentialService;
        this.frontendUserService = frontendUserService;
    }


    /**
     * Sets the dto to the userCredential model.
     *
     * @param userCredential    the userCredential model where the dto should be set.
     * @param userCredentialDto the dto to be set.
     * @return the merged hardware object.
     */
    protected UserCredential setDto(UserCredential userCredential, UserCredentialDto userCredentialDto) {

        CloudApi cloudApi = cloudApiService.getById(userCredentialDto.cloudApi);
        checkState(cloudApi != null, "Could not retrieve cloudApi for id: " + userCredentialDto.cloudApi);
        userCredential.setCloudApi(cloudApi);

        Credential credential = credentialService.getById(userCredentialDto.credential);
        checkState(credential != null, "Could not retrieve credential for id: " + userCredentialDto.credential);
        userCredential.setCredential(credential);

        FrontendUser frontendUser = frontendUserService.getById(userCredentialDto.frontendUser);
        checkState(frontendUser != null, "Could not retrieve frontendUser for id: " + userCredentialDto.frontendUser);
        userCredential.setFrontendUser(frontendUser);

        return userCredential;
    }

    @Override
    public UserCredential toModel(UserCredentialDto dto) {
        checkNotNull(dto);
        return this.setDto(new UserCredential(), dto);
    }

    @Override
    public UserCredential toModel(UserCredentialDto dto, UserCredential model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public UserCredentialDto toDto(UserCredential model) {
        checkNotNull(model);
        return new UserCredentialDtoBuilder()
                .cloudApi(model.getCloudApi().getId())
                .credential(model.getCredential().getId())
                .frontendUser(model.getFrontendUser().getId())
                .build();
    }
}
