import com.google.inject.Inject;
import models.FrontendUser;
import models.service.impl.FrontendUserService;

/**
 * Created by daniel on 25.11.14.
 */
public class InitialData {

    private final FrontendUserService frontendUserService;

    @Inject
    public InitialData(FrontendUserService frontendUserService) {
        this.frontendUserService = frontendUserService;
    }

    public void loadInitialData() {
        if (frontendUserService.getAll().isEmpty()) {
            FrontendUser frontendUser = new FrontendUser("John", "Doe", "admin", "john.doe@example.com");
            frontendUserService.save(frontendUser);
        }
    }


}
