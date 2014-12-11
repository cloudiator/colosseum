package models.service.impl;

import com.google.inject.Inject;
import models.UserCredential;
import models.repository.api.UserCredentialRepository;
import models.service.api.UserCredentialServiceInterface;
import models.service.impl.generic.ModelService;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class UserCredentialService extends ModelService<UserCredential> implements UserCredentialServiceInterface {

    @Inject
    public UserCredentialService(UserCredentialRepository userCredentialRepository){super(userCredentialRepository);}
}
