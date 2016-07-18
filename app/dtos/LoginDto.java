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

import models.service.FrontendUserService;
import play.Logger;

/**
 * Created by daniel on 10.11.14.
 */
public final class LoginDto {

    private String email;
    private String password;
    private String tenant;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String validate() {
        try {
            if (References.frontendUserService.get().authenticate(email, password, tenant)
                == null) {
                return "Invalid user, password or tenant.";
            }
        } catch (Exception e) {
            Logger.error("Error while authenticating user.", e);
            return "Invalid user, password or tenant.";
        }
        return null;
    }


    public static class References {

        @Inject private static Provider<FrontendUserService> frontendUserService;

        private References() {
        }

    }
}
