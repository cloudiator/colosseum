package models.repository.api;

import com.google.inject.ImplementedBy;
import models.ApiAccessToken;
import models.FrontendUser;
import models.repository.api.generic.ModelRepository;
import models.repository.impl.ApiAccessTokenRepositoryJpa;

import java.util.List;

/**
 * Created by daniel on 19.12.14.
 */
@ImplementedBy(ApiAccessTokenRepositoryJpa.class)
public interface ApiAccessTokenRepository extends ModelRepository<ApiAccessToken> {

    public List<ApiAccessToken> findByFrontendUser(FrontendUser frontendUser);

}
