package components.auth;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.inject.Singleton;
import de.uniulm.omi.cloudiator.common.Password;
import models.FrontendUser;
import play.Play;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 24.05.16.
 */
@Singleton public class TokenRepositoryImpl implements TokenRepository {

    private static final long VALIDITY =
        Play.application().configuration().getLong("colosseum.auth.token.validity");

    private static Cache<String, Token> cache =
        CacheBuilder.newBuilder().expireAfterWrite(VALIDITY, TimeUnit.MILLISECONDS).build();

    TokenRepositoryImpl() {
    }

    private Token createToken(FrontendUser frontendUser) {
        long now = System.currentTimeMillis();
        return new Token(now, now + VALIDITY, Password.getInstance().generateToken(),
            frontendUser.getId());
    }

    @Override public Token newToken(FrontendUser frontendUser) {
        Token token = createToken(frontendUser);
        cache.put(token.token(), token);
        return token;
    }

    private Optional<Token> getToken(String token) {
        return Optional.ofNullable(cache.getIfPresent(token));
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
