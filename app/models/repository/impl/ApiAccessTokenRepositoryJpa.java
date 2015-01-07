package models.repository.impl;

import models.ApiAccessToken;
import models.FrontendUser;
import models.repository.api.ApiAccessTokenRepository;
import models.repository.impl.generic.ModelRepositoryJpa;

import java.util.List;

/**
 * Created by daniel on 19.12.14.
 */
public class ApiAccessTokenRepositoryJpa extends ModelRepositoryJpa<ApiAccessToken> implements ApiAccessTokenRepository {

    @Override
    public List<ApiAccessToken> findByFrontendUser(final FrontendUser frontendUser) {
        //noinspection unchecked
        return em().createQuery("from ApiAccessToken aat where frontendUser = :frontendUser").setParameter("frontendUser", frontendUser).getResultList();
    }
}
