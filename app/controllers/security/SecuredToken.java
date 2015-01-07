package controllers.security;

import com.google.inject.Inject;
import com.google.inject.Provider;
import models.FrontendUser;
import models.service.api.ApiAccessTokenServiceInterface;
import models.service.api.FrontendUserServiceInterface;
import play.db.jpa.JPA;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by daniel on 19.12.14.
 */

public class SecuredToken extends Security.Authenticator {

    public static class References {

        @Inject
        public static Provider<ApiAccessTokenServiceInterface> apiAccessTokenServiceProvider;

        @Inject
        public static Provider<FrontendUserServiceInterface> frontendUserServiceInterfaceProvider;
    }

    @Override
    public String getUsername(final Http.Context context) {

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

        final FrontendUser frontendUser;
        try {
            frontendUser = JPA.withTransaction(() -> References.frontendUserServiceInterfaceProvider.get().getById(userId));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }

        if (frontendUser == null) {
            return null;
        }

        try {
            if (JPA.withTransaction(() -> References.apiAccessTokenServiceProvider.get().isValid(token, frontendUser))) {
                return frontendUser.getMail();
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }

        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        return unauthorized();
    }
}
