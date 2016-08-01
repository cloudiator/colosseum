package components.auth;

import com.google.inject.Inject;

import java.util.Optional;

import models.ApiAccessToken;
import models.FrontendUser;
import models.service.ApiAccessTokenService;
import models.service.FrontendUserService;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 24.05.16.
 */
public class DatabaseTokenStore implements TokenStore {

    private final FrontendUserService frontendUserService;
    private final ApiAccessTokenService apiAccessTokenService;

    @Inject DatabaseTokenStore(FrontendUserService frontendUserService,
        ApiAccessTokenService apiAccessTokenService) {
        this.frontendUserService = frontendUserService;
        this.apiAccessTokenService = apiAccessTokenService;
    }

    @Override public void store(Token token) {
        FrontendUser frontendUser = frontendUserService.getById(token.userId());
        checkState(frontendUser != null, "user in token not in db");
        ApiAccessToken apiAccessToken = new ApiAccessToken(frontendUser, token.token());
        apiAccessTokenService.save(apiAccessToken);
    }

    @Override public Optional<Token> retrieve(String token) {
        final ApiAccessToken apiAccessToken = this.apiAccessTokenService.findByToken(token);
        if (apiAccessToken == null) {
            return Optional.empty();
        }
        return Optional.of(Token.builder().createdOn(apiAccessToken.getCreatedOn())
            .expiresAt(apiAccessToken.getExpiresAt()).token(apiAccessToken.getToken())
            .userId(apiAccessToken.getFrontendUser().getId()).build());
    }
}
