package controllers.security;

import play.mvc.Http;
import play.mvc.Security;

/**
 * Created by daniel on 03.07.15.
 */
public abstract class TenantAwareAuthenticator extends Security.Authenticator {

    public final String SEPARATOR = ":";

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
