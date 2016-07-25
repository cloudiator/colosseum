package components.model;

import models.Application;

/**
 * Created by daniel on 19.06.16.
 */
public interface ModelValidationService {

    void validate(Application application) throws ModelValidationException;

}
