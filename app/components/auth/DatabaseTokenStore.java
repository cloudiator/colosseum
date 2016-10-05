package components.auth;

import com.google.inject.Inject;
import models.ApiAccessToken;
import models.FrontendUser;
import models.service.ApiAccessTokenService;
import models.service.FrontendUserService;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;

/**
 * A {@link TokenStore} implementation using the attached database for persisting tokens.
 */
public class DatabaseTokenStore implements TokenStore {

    private final FrontendUserService frontendUserService;
    private final ApiAccessTokenService apiAccessTokenService;
    private final TokenValidity tokenValidity;

    @Inject DatabaseTokenStore(FrontendUserService frontendUserService,
        ApiAccessTokenService apiAccessTokenService, TokenValidity tokenValidity) {
        this.frontendUserService = frontendUserService;
        this.apiAccessTokenService = apiAccessTokenService;
        this.tokenValidity = tokenValidity;
    }

    @Override public void store(Token token) {
        FrontendUser frontendUser = frontendUserService.getById(token.userId());
        checkState(frontendUser != null, "user in token not in db");
        ApiAccessToken apiAccessToken = new ApiAccessToken(frontendUser, token.token());
        apiAccessTokenService.save(apiAccessToken);
    }

    @Override public Optional<Token> retrieve(String token) {
        if(tokenValidity.validity() != TokenValidity.INFINITE_VALIDITY) {
            apiAccessTokenService.deleteExpiredTokens(tokenValidity.deadline());
        }
        final ApiAccessToken apiAccessToken = apiAccessTokenService.findByToken(token);
        if (apiAccessToken == null) {
            return Optional.empty();
        }
        return Optional.of(Token.builder().createdOn(apiAccessToken.getCreatedOn())
            .expiresAt(apiAccessToken.getExpiresAt()).token(apiAccessToken.getToken())
            .userId(apiAccessToken.getFrontendUser().getId()).build());
    }
}
