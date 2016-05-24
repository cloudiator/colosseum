package components.auth;

import models.service.ApiAccessTokenService;
import models.service.FrontendUserService;

import java.util.Optional;

/**
 * Created by daniel on 24.05.16.
 */
public class DatabaseTokenStore implements TokenStore {

    DatabaseTokenStore(FrontendUserService frontendUserService,
        ApiAccessTokenService apiAccessTokenService) {

    }

    @Override public void store(Token token) {
        //todo implement storing
    }

    @Override public Optional<Token> retrieve(String token) {
        //todo implement retrieving
        return null;
    }
}
