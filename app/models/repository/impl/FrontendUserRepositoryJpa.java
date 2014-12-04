package models.repository.impl;

import models.FrontendUser;
import models.repository.api.FrontendUserRepository;
import models.repository.impl.generic.ModelRepositoryJpa;

import static models.util.JpaResultHelper.getSingleResultOrNull;

/**
 * Created by daniel on 03.11.14.
 */
public class FrontendUserRepositoryJpa extends ModelRepositoryJpa<FrontendUser> implements FrontendUserRepository {


    @Override
    public FrontendUser findByMail(final String mail) {
        return (FrontendUser) getSingleResultOrNull(em()
                .createQuery("from FrontendUser fu where mail = :mail")
                .setParameter("mail", mail));
    }
}
