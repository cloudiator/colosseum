package models.service.api;

import com.google.inject.ImplementedBy;
import models.Credential;
import models.service.api.generic.ModelServiceInterface;
import models.service.impl.CredentialService;

/**
 * Created by daniel seybold on 10.12.2014.
 */
@ImplementedBy(CredentialService.class)
public interface CredentialServiceInterface extends ModelServiceInterface<Credential> {
}
