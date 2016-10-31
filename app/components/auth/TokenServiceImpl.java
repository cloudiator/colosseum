package components.auth;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.FrontendUser;
import play.Logger;
import util.logging.Loggers;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Basic {@link TokenService} implementation, using a {@link TokenStore} for storing the generated
 * tokens.
 */
@Singleton
class TokenServiceImpl implements TokenService {

    private final Logger.ALogger LOGGER = Loggers.of(Loggers.AUTH);
    private final TokenStore tokenStore;
    private final TokenValidity tokenValidity;

    @Inject
    TokenServiceImpl(TokenStore tokenStore, TokenValidity tokenValidity) {
        checkNotNull(tokenValidity);
        this.tokenValidity = tokenValidity;
        checkNotNull(tokenStore);
        this.tokenStore = tokenStore;
    }

    private Token newForUser(FrontendUser frontendUser) {
        long now = System.currentTimeMillis();
        long validUntil = tokenValidity.deadline();
        Token token = Token.builder().createdOn(now).expiresAt(validUntil).randomToken()
                .userId(frontendUser.getId()).build();
        LOGGER.debug(String.format("Created new token %s for user %s.", token, frontendUser));
        return token;
    }

    @Override
    public Token newToken(FrontendUser frontendUser) {
        checkNotNull(frontendUser);
        Token token = newForUser(frontendUser);
        tokenStore.store(token);
        return token;
    }

    private Optional<Token> getToken(String token) {
        return tokenStore.retrieve(token);
    }

    @Override
    public boolean isTokenValidForUser(String token, FrontendUser frontendUser) {
        LOGGER.debug(String.format("Checking token validity for token %s and frontendUser %s.", token, frontendUser));
        checkNotNull(token);
        checkNotNull(frontendUser);
        Optional<Token> retrievedToken = getToken(token);
        if (!retrievedToken.isPresent()) {
            LOGGER.debug("Token %s is invalid for user %s as it could not be retrieved from the store %s.", token, frontendUser, tokenStore);
            return false;
        }
        checkState(retrievedToken.get().token().equals(token));

        if (retrievedToken.get().userId() != frontendUser.getId()) {
            LOGGER.debug(String.format("Token %s is invalid for user %s as user id does not match.", token, frontendUser));
            return false;
        }

        if (tokenValidity.isExpired(retrievedToken.get())) {
            LOGGER.debug(String.format("Token %s is invalid for user %s as it has expired.", token, frontendUser));
            return false;
        }

        LOGGER.debug(String.format("Token %s is valid for user %s.", token, frontendUser));
        return true;

    }
}
