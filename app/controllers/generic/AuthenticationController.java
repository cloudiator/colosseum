package controllers.generic;

import controllers.security.TenantAwareAuthenticator;
import models.Cloud;
import models.CloudCredential;
import models.FrontendUser;
import models.Tenant;
import models.service.api.FrontendUserService;
import models.service.api.generic.ModelService;
import play.mvc.Controller;

import javax.annotation.Nullable;
import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 03.07.15.
 */
public class AuthenticationController extends Controller {

    private final FrontendUserService frontendUserService;
    private final ModelService<Tenant> tenantModelService;

    @Inject public AuthenticationController(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService) {

        checkNotNull(frontendUserService);
        checkNotNull(tenantModelService);

        this.frontendUserService = frontendUserService;
        this.tenantModelService = tenantModelService;
    }

    private String[] getAuth() {
        String[] auth = request().username().split(TenantAwareAuthenticator.SEPARATOR);
        checkState(auth.length == 2);
        return auth;
    }

    protected FrontendUser getUser() {
        String mail = getAuth()[1];
        FrontendUser frontendUser = frontendUserService.getByMail(mail);
        checkState(frontendUser != null);
        return frontendUser;
    }

    protected Tenant getActiveTenant() {
        String tenantString = getAuth()[0];
        Tenant tenant = tenantModelService.getByUuid(tenantString);
        checkState(tenant != null);
        return tenant;
    }

    @Nullable protected CloudCredential getCloudCredential(Cloud cloud) {
        Tenant tenant = getActiveTenant();
        for (CloudCredential cloudCredential : tenant.getCloudCredentials()) {
            if (cloudCredential.getCloud().equals(cloud)) {
                return cloudCredential;
            }
        }
        return null;
    }
}
