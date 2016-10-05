package components.auth;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.FrontendUser;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Basic {@link TokenService} implementation, using a {@link TokenStore} for storing the generated
 * tokens.
 */
@Singleton class TokenServiceImpl implements TokenService {

    private final TokenStore tokenStore;
    private final TokenValidity tokenValidity;

    @Inject TokenServiceImpl(TokenStore tokenStore, TokenValidity tokenValidity) {
        checkNotNull(tokenValidity);
        this.tokenValidity = tokenValidity;
        checkNotNull(tokenStore);
        this.tokenStore = tokenStore;
    }

    private Token newForUser(FrontendUser frontendUser) {
        long now = System.currentTimeMillis();
        long validUntil = tokenValidity.deadline();
        return Token.builder().createdOn(now).expiresAt(validUntil).randomToken()
            .userId(frontendUser.getId()).build();
    }

    @Override public Token newToken(FrontendUser frontendUser) {
        checkNotNull(frontendUser);
        Token token = newForUser(frontendUser);
        tokenStore.store(token);
        return token;
    }

    private Optional<Token> getToken(String token) {
        return tokenStore.retrieve(token);
    }

    @Override public boolean isTokenValidForUser(String token, FrontendUser frontendUser) {
        checkNotNull(token);
        checkNotNull(frontendUser);
        Optional<Token> optional = getToken(token);
        if (!optional.isPresent()) {
            return false;
        }
        checkState(optional.get().token().equals(token));
        return optional.get().userId() == frontendUser.getId() && !tokenValidity
            .isExpired(optional.get());

    }
}
