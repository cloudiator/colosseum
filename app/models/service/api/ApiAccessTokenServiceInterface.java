package models.service.api;

import com.google.inject.ImplementedBy;
import models.ApiAccessToken;
import models.FrontendUser;
import models.service.api.generic.ModelServiceInterface;
import models.service.impl.ApiAccessTokenService;

/**
 * Created by daniel on 19.12.14.
 */
@ImplementedBy(ApiAccessTokenService.class)
public interface ApiAccessTokenServiceInterface extends ModelServiceInterface<ApiAccessToken> {

    public boolean isValid(String token, FrontendUser frontendUser);
}
