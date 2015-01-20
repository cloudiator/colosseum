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

package dtos.builders;

import dtos.UserCredentialDto;

public class UserCredentialDtoBuilder {
    private Long cloudApi;
    private Long credential;
    private Long frontendUser;

    public UserCredentialDtoBuilder cloudApi(Long cloudApi) {
        this.cloudApi = cloudApi;
        return this;
    }

    public UserCredentialDtoBuilder credential(Long credential) {
        this.credential = credential;
        return this;
    }

    public UserCredentialDtoBuilder frontendUser(Long frontendUser) {
        this.frontendUser = frontendUser;
        return this;
    }

    public UserCredentialDto build() {
        return new UserCredentialDto(cloudApi, credential, frontendUser);
    }
}