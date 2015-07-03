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

package controllers.security;

import com.google.inject.Inject;
import com.google.inject.Provider;
import models.FrontendUser;
import models.Tenant;
import models.service.api.ApiAccessTokenService;
import models.service.api.FrontendUserService;
import play.db.jpa.JPA;
import play.mvc.Http;
import play.mvc.Result;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;

/**
 * Created by daniel on 19.12.14.
 */

public class SecuredToken extends TenantAwareAuthenticator {

    @Nullable private FrontendUser getFrontendUser(Http.Context context) {
        final String token = context.request().getHeader("X-Auth-Token");

        long userId;
        try {
            userId = Long.parseLong(context.request().getHeader("X-Auth-UserId"));
        } catch (NumberFormatException e) {
            return null;
        }

        if (token == null || token.isEmpty()) {
            return null;
        }
        if (userId == 0) {
            return null;
        }

        //remember the entity manager
        //workaround for https://github.com/playframework/playframework/pull/3388
        final EntityManager em = JPA.em();
        FrontendUser frontendUser;
        try {
            frontendUser = JPA.withTransaction(
                () -> References.frontendUserServiceInterfaceProvider.get().getById(userId));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
        //workaround continue. Bind the old one.
        JPA.bindForCurrentThread(em);

        //remember the entity manager
        //workaround for https://github.com/playframework/playframework/pull/3388
        final EntityManager em1 = JPA.em();
        try {
            final FrontendUser finalFrontendUser = frontendUser;
            if (!JPA.withTransaction(() -> References.apiAccessTokenServiceProvider.get()
                .isValid(token, finalFrontendUser))) {
                frontendUser = null;
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
        //workaround continue. Bind the old one.
        JPA.bindForCurrentThread(em1);

        return frontendUser;
    }

    @Override public String getUser(Http.Context context) {
        FrontendUser frontendUser = this.getFrontendUser(context);
        if (frontendUser == null) {
            return null;
        } else {
            return frontendUser.getMail();
        }
    }

    @Override public String getTenant(Http.Context context) {
        FrontendUser frontendUser = this.getFrontendUser(context);

        if (frontendUser == null) {
            return null;
        }

        String tenant = context.request().getHeader("X-Tenant");

        Tenant foundTenant = null;
        for (Tenant searchTenant : frontendUser.getTenants()) {
            if (searchTenant.getUuid().equals(tenant)) {
                foundTenant = searchTenant;
                break;
            }
        }

        if (foundTenant != null) {
            return foundTenant.getUuid();
        }
        return null;
    }

    @Override public Result onUnauthorized(Http.Context context) {
        return unauthorized();
    }


    public static class References {
        @Inject private static Provider<ApiAccessTokenService> apiAccessTokenServiceProvider;
        @Inject private static Provider<FrontendUserService> frontendUserServiceInterfaceProvider;
    }
}
