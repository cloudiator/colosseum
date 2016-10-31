package components.auth;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.inject.Singleton;
import play.Logger;
import util.logging.Loggers;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * A {@link TokenStore} implementation relying on Guava's Cache for
 * storing tokens ({@link Cache}).
 * <p>
 * Stores tokens in memory, and automatically cleans already expired tokens from
 * the storage.
 */
@Singleton
public class CacheBasedTokenStore implements TokenStore {

    private Logger.ALogger LOGGER = Loggers.of(Loggers.AUTH);

    private final Cache<String, Token> cache;

    public CacheBasedTokenStore(TokenValidity tokenValidity) {

        final CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        if (tokenValidity.validity() != TokenValidity.INFINITE_VALIDITY) {
            cacheBuilder.expireAfterWrite(tokenValidity.validity(), TimeUnit.MILLISECONDS);
        }
        cache = cacheBuilder.build();
        LOGGER.debug(String.format("%s initialized cache %s.", this, cache));
    }

    @Override
    public void store(Token token) {
        LOGGER.debug(String.format("%s is storing token %s.", this, token));
        cache.put(token.token(), token);
    }

    @Override
    public Optional<Token> retrieve(String token) {
        Optional<Token> tokenOptional = Optional.ofNullable(cache.getIfPresent(token));
        LOGGER.debug(String.format("%s retrieved token %s", this, tokenOptional));
        return tokenOptional;
    }

}
