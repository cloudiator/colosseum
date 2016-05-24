package components.auth;

import com.google.inject.ImplementedBy;

import java.util.Optional;

/**
 * Created by daniel on 24.05.16.
 */
@ImplementedBy(CacheBasedTokenStore.class) public interface TokenStore {
    void store(Token token);

    Optional<Token> retrieve(String token);
}
