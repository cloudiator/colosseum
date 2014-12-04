package models.service.impl;

import com.google.inject.Inject;
import components.security.Password;
import models.FrontendUser;
import models.repository.api.FrontendUserRepository;
import models.service.api.FrontendUserServiceInterface;
import models.service.impl.generic.ModelService;
import org.apache.commons.codec.binary.Base64;
import play.Logger;

/**
 * Created by daniel on 03.11.14.
 */
public class FrontendUserService extends ModelService<FrontendUser> implements FrontendUserServiceInterface {

    @Inject
    public FrontendUserService(FrontendUserRepository frontendUserRepository) {
        super(frontendUserRepository);
    }

    @Override
    public FrontendUser getByMail(final String mail) {
        return ((FrontendUserRepository)this.modelRepository).findByMail(mail);
    }

    @Override
    public FrontendUser authenticate(String mail, String password) {
        Logger.info(String.format(
                "Trying to authenticate %s using password ****", mail));
        FrontendUser fe = this.getByMail(mail);
        if (fe == null) {
            Logger.warn("Authentication failed, could not retrieve user from db");
            return null;
        }

        if (Password.getInstance().check(password.toCharArray(),
                fe.getPassword().toCharArray(), Base64.decodeBase64(fe.getSalt()))) {
            Logger.info("Authetication success. Authenticated as user with id="
                    + fe.getId());
            return fe;
        } else {
            Logger.warn("Authentication failed. Could not match password");
            return null;
        }
    }
}
