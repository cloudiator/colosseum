package components.auth;

import java.util.Optional;

/**
 * Interface for TokenStore implementations.
 * <p>
 * Token Stores store Authentication Tokens ({@link Token}) and offers
 * method to retrieve those tokens.
 */
public interface TokenStore {

    /**
     * Stores a token.
     *
     * @param token stores the given token within the token store.
     */
    void store(Token token);

    /**
     * Retrieves an {@link Optional} token from the database.
     * <p>
     * You are not guaranteed to be able retrieve previously stored tokens
     * of they are already expired.
     * <p>
     * You are also not guaranteed that the token is still valid when retrieving it.
     *
     * @param token the identifier for the token.
     * @return the token if it is present, otherwise {@link Optional#EMPTY}
     */
    Optional<Token> retrieve(String token);
}
