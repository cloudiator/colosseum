package models.service.impl;

import com.google.inject.Inject;
import models.Credential;
import models.repository.api.CredentialRepository;
import models.service.api.CredentialServiceInterface;
import models.service.impl.generic.ModelService;

/**
 * Created by daniel seybold on 10.12.2014.
 */
public class CredentialService extends ModelService<Credential> implements CredentialServiceInterface {

    @Inject
    CredentialService(CredentialRepository credentialRepository){super(credentialRepository);}
}
