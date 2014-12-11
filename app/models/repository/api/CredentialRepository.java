package models.repository.api;

import com.google.inject.ImplementedBy;
import models.Credential;
import models.repository.api.generic.ModelRepository;
import models.repository.impl.CredentialRepositoryJpa;

/**
 * Created by daniel seybold on 10.12.2014.
 */
@ImplementedBy(CredentialRepositoryJpa.class)
public interface CredentialRepository extends ModelRepository<Credential> {
}
