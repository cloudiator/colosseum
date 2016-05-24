package components.auth;

import com.google.inject.ImplementedBy;
import models.FrontendUser;

/**
 * Created by daniel on 24.05.16.
 */
@ImplementedBy(TokenRepositoryImpl.class) public interface TokenRepository {

    Token newToken(FrontendUser frontendUser);

    boolean isTokenValidForUser(String token, FrontendUser frontendUser);
}
