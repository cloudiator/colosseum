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

import dtos.CredentialDto;
import models.Credential;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class CredentialConverter extends BaseConverter<Credential, CredentialDto> {

    /**
     * Sets the dto to the hardware model.
     *
     * @param credential    the credential model where the dto should be set.
     * @param credentialDto the dto to be set.
     * @return the merged credential object.
     */
    protected Credential setDto(Credential credential, CredentialDto credentialDto) {
        credential.setUser(credentialDto.user);
        credential.setSecret(credentialDto.secret);

        return credential;
    }

    @Override
    public Credential toModel(CredentialDto dto) {
        checkNotNull(dto);
        return setDto(new Credential(), dto);
    }

    @Override
    public Credential toModel(CredentialDto dto, Credential model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public CredentialDto toDto(Credential model) {
        checkNotNull(model);
        return new CredentialDto(model.getUser(), model.getSecret());
    }
}
