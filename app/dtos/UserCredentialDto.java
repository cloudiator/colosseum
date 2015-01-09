package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.impl.ValidatableDto;
import models.CloudApi;
import models.Credential;
import models.FrontendUser;
import models.service.api.CloudApiServiceInterface;
import models.service.api.CredentialServiceInterface;
import models.service.api.FrontendUserServiceInterface;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class UserCredentialDto extends ValidatableDto {

    public static class References {
        @Inject
        public static Provider<CloudApiServiceInterface> cloudApiService;

        @Inject
        public static Provider<CredentialServiceInterface> credentialService;

        @Inject
        public static Provider<FrontendUserServiceInterface> frontendUserService;
    }


    public Long cloudApi;

    public Long credential;

    public Long frontendUser;

    public UserCredentialDto(){

    }

    public UserCredentialDto(Long cloudApi, Long credential, Long frontendUser){

        this.cloudApi = cloudApi;
        this.credential = credential;
        this.frontendUser = frontendUser;
    }

    @Override
    public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();

        //validate cloudApi reference
        CloudApi cloudApi = null;
        if (this.cloudApi == null) {
            errors.add(new ValidationError("cloudApi", "The cloudApi is required."));
        } else {
            cloudApi = References.cloudApiService.get().getById(this.cloudApi);
            if (cloudApi == null) {
                errors.add(new ValidationError("cloudApi", "The given cloudApi is invalid."));
            }
        }

        //validate Credential reference
        Credential credential = null;
        if (this.credential == null) {
            errors.add(new ValidationError("credential", "The credential is required."));
        } else {
            credential = References.credentialService.get().getById(this.credential);
            if (credential == null) {
                errors.add(new ValidationError("credential", "The given credential is invalid."));
            }
        }

        //validate cloudApi reference
        FrontendUser frontendUser = null;
        if (this.frontendUser == null) {
            errors.add(new ValidationError("frontendUser", "The frontendUser is required."));
        } else {
            frontendUser = References.frontendUserService.get().getById(this.frontendUser);
            if (frontendUser == null) {
                errors.add(new ValidationError("frontendUser", "The given frontendUser is invalid."));
            }
        }

        return errors;
    }
}
