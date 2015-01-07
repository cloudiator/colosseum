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

    @Override
    public String getUsername(Http.Context context) {

        String tokenUserName = this.securedToken.getUsername(context);
        String sessionUserName = this.securedSession.getUsername(context);

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
        return unauthorized();
    }
}
