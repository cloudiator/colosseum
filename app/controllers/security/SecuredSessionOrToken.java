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

package controllers.security;

import play.mvc.Http;
import play.mvc.Result;

/**
 * Created by daniel on 07.01.15.
 */
public class SecuredSessionOrToken extends SecuredToken {

    private final SecuredSession securedSession;
    private final SecuredToken securedToken;

    public SecuredSessionOrToken() {
        securedSession = new SecuredSession();
        securedToken = new SecuredToken();
    }

    protected String authorizedSession(Http.Context context) {
        return this.securedSession.getUsername(context);
    }

    protected String authorizedToken(Http.Context context) {
        return this.securedToken.getUsername(context);
    }

    @Override
    public String getUsername(Http.Context context) {

        String tokenUserName = authorizedToken(context);
        String sessionUserName = authorizedSession(context);

        if (tokenUserName != null) {
            return tokenUserName;
        }
        if (sessionUserName != null) {
            return sessionUserName;
        }

        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        return unauthorized("Unauthorized: Login at /login");
    }
}
