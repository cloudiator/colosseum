package models.repository.api;

import com.google.inject.ImplementedBy;
import models.UserCredential;
import models.repository.api.generic.ModelRepository;
import models.repository.impl.UserCredentialRepositoryJpa;

/**
 * Created by daniel seybold on 11.12.2014.
 */
@ImplementedBy(UserCredentialRepositoryJpa.class)
public interface UserCredentialRepository extends ModelRepository<UserCredential> {
}
