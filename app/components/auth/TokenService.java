package components.auth;

import models.FrontendUser;

/**
 * Token Service creates and validates Authentication Tokens (@link {@link Token}) for
 * users of the system ({@link FrontendUser}).
 */
public interface TokenService {

    /**
     * Creates a new token for the given {@link FrontendUser}
     *
     * @param frontendUser the frontendUser to generate the token for.
     * @return a token generated for the user.
     */
    Token newToken(FrontendUser frontendUser);

    /**
     * Checks if the token identified by the given id is
     * a) valid for this {@link FrontendUser}
     * b) not expired yet.
     *
     * @param token        the identifier for the token
     * @param frontendUser the {@link FrontendUser} you want to check
     * @return true if the token is still valid for this user, false if not.
     */
    boolean isTokenValidForUser(String token, FrontendUser frontendUser);
}
