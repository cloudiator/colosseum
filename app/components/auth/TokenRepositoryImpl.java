package components.auth;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.uniulm.omi.cloudiator.common.Password;
import models.FrontendUser;
import play.Play;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 24.05.16.
 */
@Singleton public class TokenRepositoryImpl implements TokenRepository {

    public static final long VALIDITY =
        Play.application().configuration().getLong("colosseum.auth.token.validity");
    private final TokenStore tokenStore;

    @Inject TokenRepositoryImpl(TokenStore tokenStore) {
        checkNotNull(tokenStore);
        this.tokenStore = tokenStore;
    }

    private Token createToken(FrontendUser frontendUser) {
        long now = System.currentTimeMillis();
        return new Token(now, now + VALIDITY, Password.getInstance().generateToken(),
            frontendUser.getId());
    }

    @Override public Token newToken(FrontendUser frontendUser) {
        Token token = createToken(frontendUser);
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
