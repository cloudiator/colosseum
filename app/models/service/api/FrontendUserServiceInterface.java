package models.service.api;

import com.google.inject.ImplementedBy;
import models.FrontendUser;
import models.service.api.generic.ModelServiceInterface;
import models.service.impl.FrontendUserService;

/**
 * Created by daniel on 03.11.14.
 */
@ImplementedBy(FrontendUserService.class)
public interface FrontendUserServiceInterface extends ModelServiceInterface<FrontendUser>{

    public FrontendUser getByMail(String mail);

    /**
     * Tries to authenticate the user.
     *
     * @param mail
     *            the mail address given by the user
     * @param password
     *            the plain text given by the user.
     *
     * @return the user object if auth was successful, null if not.
     */
    public FrontendUser authenticate(String mail, String password);

}
