package components.auth;

import com.google.common.base.MoreObjects;
import de.uniulm.omi.cloudiator.common.Password;
import play.Play;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 24.05.16.
 */
public class Token {

    public static final long VALIDITY =
        Play.application().configuration().getLong("colosseum.auth.token.validity");

    private final long createdOn;
    private final long expiresAt;
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

    public boolean isExpired() {
        return System.currentTimeMillis() > expiresAt;
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

    public static class TokenBuilder {

        private long createdOn;
        private long expiresAt;
        private String token;
        private long userId;

        public TokenBuilder createdOn(long createdOn) {
            this.createdOn = createdOn;
            return this;
        }

        public TokenBuilder expiresAt(long expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public TokenBuilder validFromNow() {
            long now = System.currentTimeMillis();
            this.createdOn = now;
            this.expiresAt = now + VALIDITY;
            return this;
        }

        public TokenBuilder token(String token) {
            this.token = token;
            return this;
        }

        public TokenBuilder randomToken() {
            this.token = Password.getInstance().generateToken();
            return this;
        }

        public TokenBuilder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public Token build() {
            return new Token(createdOn, expiresAt, token, userId);
        }

    }
}
