package components.auth;

import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 24.05.16.
 */
public class Token {

    private final long createdOn;
    private final long expiresAt;
    private final String token;
    private final long userId;

    Token(long createdOn, long expiresAt, String token, long userId) {
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

    public String toString() {
        return MoreObjects.toStringHelper(this).add("createOn", createdOn)
            .add("expiresAt", expiresAt).add("token", token).add("userId", userId).toString();
    }
}
