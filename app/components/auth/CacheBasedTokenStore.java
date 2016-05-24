package components.auth;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created by daniel on 24.05.16.
 */
public class CacheBasedTokenStore implements TokenStore {

    private static Cache<String, Token> cache = CacheBuilder.newBuilder()
        .expireAfterWrite(TokenRepositoryImpl.VALIDITY, TimeUnit.MILLISECONDS).build();

    @Override public void store(Token token) {
        cache.put(token.token(), token);
    }

    @Override public Optional<Token> retrieve(String token) {
        return Optional.ofNullable(cache.getIfPresent(token));
    }

}
