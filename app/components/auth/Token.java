package components.auth;

import com.google.common.base.MoreObjects;
import de.uniulm.omi.cloudiator.common.Password;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 24.05.16.
 */
public class Token {

    private final long createdOn;
    private final Long expiresAt;
    private final String token;
    private final long userId;

    private Token(long createdOn, long expiresAt, String token, long userId) {
        checkArgument(createdOn > 0);
        checkArgument(expiresAt > 0);
        checkNotNull(token);
        checkArgument(!token.isEmpty());
        checkArgument(userId > 0);

        this.createdOn = createdOn;
        this.expiresAt = expiresAt;
        this.token = token;
        this.userId = userId;
    }

    public String token() {
        return token;
    }

    public long userId() {
        return userId;
    }

    public long createdOn() {
        return createdOn;
    }

    public long expiresAt() {
        return expiresAt;
    }

    public static TokenBuilder builder() {
        return new TokenBuilder();
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("createOn", createdOn)
            .add("expiresAt", expiresAt).add("token", token).add("userId", userId).toString();
    }

    static class TokenBuilder {

        private long createdOn;
        private long expiresAt;
        private String token;
        private long userId;

        TokenBuilder createdOn(long createdOn) {
            this.createdOn = createdOn;
            return this;
        }

        TokenBuilder expiresAt(long expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        TokenBuilder token(String token) {
            this.token = token;
            return this;
        }

        TokenBuilder randomToken() {
            this.token = Password.getInstance().generateToken();
            return this;
        }

        TokenBuilder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public Token build() {
            return new Token(createdOn, expiresAt, token, userId);
        }

    }
}
