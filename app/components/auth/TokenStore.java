package components.auth;

import java.util.Optional;

/**
 * Created by daniel on 24.05.16.
 */
public interface TokenStore {

    void store(Token token);

    Optional<Token> retrieve(String token);
}
