/*
 * Copyright (c) 2014-2016 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package controllers.internal;

import play.mvc.Http;
import play.mvc.Security;

/**
 * Created by daniel on 03.07.15.
 */
public abstract class TenantAwareAuthenticator extends Security.Authenticator {

    public static final String SEPARATOR = ":";

    public abstract String getUser(Http.Context context);

    public abstract String getTenant(Http.Context context);

    @Override public String getUsername(Http.Context context) {
        final String user = getUser(context);
        final String tenant = getTenant(context);

        if (user != null && tenant != null) {
            return tenant + SEPARATOR + user;
        }
        return null;
    }
}
