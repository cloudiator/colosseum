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

package models;

import models.generic.Model;

import javax.persistence.Entity;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity
public class Credential extends Model {

    /**
     * Empty constructor for hibernate.
     */
    private Credential() {
    }

    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The username used for authentication.
     */
    private String user;

    /**
     * The apiKey used for authentication.
     */
    private String secret;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        checkNotNull(user);
        this.user = user;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        checkNotNull(secret);
        this.secret = secret;
    }
}
