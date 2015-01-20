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

package dtos.builders;

import dtos.FrontendUserDto;

public class FrontendUserDtoBuilder {
    private String firstName;
    private String lastName;
    private String mail;
    private String password;
    private String repeat;

    public FrontendUserDtoBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public FrontendUserDtoBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public FrontendUserDtoBuilder mail(String mail) {
        this.mail = mail;
        return this;
    }

    public FrontendUserDtoBuilder password(String password) {
        this.password = password;
        return this;
    }

    public FrontendUserDtoBuilder repeat(String repeat) {
        this.repeat = repeat;
        return this;
    }

    public FrontendUserDto build() {
        return new FrontendUserDto(firstName, lastName, mail, password, repeat);
    }
}