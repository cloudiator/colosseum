package components.auth;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.FrontendUser;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 24.05.16.
 */
@Singleton public class TokenServiceImpl implements TokenService {

    private final TokenStore tokenStore;

    @Inject TokenServiceImpl(TokenStore tokenStore) {
        checkNotNull(tokenStore);
        this.tokenStore = tokenStore;
    }

    private static Token newForUser(FrontendUser frontendUser) {
        return Token.builder().validFromNow().randomToken().userId(frontendUser.getId()).build();
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
        return optional.get().userId() == frontendUser.getId() && !optional.get().isExpired();

    }
}
