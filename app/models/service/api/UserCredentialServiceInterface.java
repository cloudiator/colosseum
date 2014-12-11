package models.service.api;

import com.google.inject.ImplementedBy;
import models.UserCredential;
import models.service.api.generic.ModelServiceInterface;
import models.service.impl.UserCredentialService;

/**
 * Created by daniel seybold on 11.12.2014.
 */
@ImplementedBy(UserCredentialService.class)
public interface UserCredentialServiceInterface extends ModelServiceInterface<UserCredential> {
}
