package components.auth;

import com.google.inject.ImplementedBy;
import models.FrontendUser;

/**
 * Created by daniel on 24.05.16.
 */
@ImplementedBy(TokenServiceImpl.class) public interface TokenService {

    Token newToken(FrontendUser frontendUser);

    boolean isTokenValidForUser(String token, FrontendUser frontendUser);
}
