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
