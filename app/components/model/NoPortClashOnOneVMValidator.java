package components.model;

import models.ApplicationInstance;

import java.util.Collections;
import java.util.Set;

/**
 * Created by daniel on 28.10.16.
 */
public class NoPortClashOnOneVMValidator implements ModelValidator<ApplicationInstance> {
    @Override
    public Set<ValidationMessage> validate(ApplicationInstance applicationInstance) {
        //TODO: validate
        return Collections.emptySet();
    }
}
