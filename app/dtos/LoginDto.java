package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import models.service.impl.FrontendUserService;
import play.Logger;

/**
 * Created by daniel on 10.11.14.
 */
public class LoginDto {

    public static class FrontendUserServiceReference {
        @Inject
        public static Provider<FrontendUserService> frontendUserService;
    }

    public String email;
    public String password;
    public Boolean rememberMe;

    public String validate() {
        try {
            if (FrontendUserServiceReference.frontendUserService.get().authenticate(email, password) == null) {
                return "Invalid user or password";
            }
        } catch (Exception e) {
            Logger.error("Error while authenticating user.", e);
            return "Invalid user or password";
        }
        return null;
    }
}
