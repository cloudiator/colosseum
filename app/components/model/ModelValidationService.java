package components.model;

import models.Application;
import models.ApplicationInstance;

/**
 * Created by daniel on 19.06.16.
 */
public interface ModelValidationService {

    void validate(Application application) throws ModelValidationException;

    void validate(ApplicationInstance applicationInstance) throws ModelValidationException;

}
