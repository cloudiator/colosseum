package components.model;

import com.google.common.collect.Sets;
import models.Application;
import models.PortRequired;

import java.util.Set;

/**
 * Created by daniel on 19.06.16.
 */
public class EveryPortRequiredFullfilledValidator implements ModelValidator<Application> {

    @Override public Set<ValidationMessage> validate(Application application) {
        Set<ValidationMessage> validationMessages = Sets.newHashSet();
        Set<PortRequired> portRequiredSet = Sets.newHashSet();
        application.getApplicationComponents().forEach(applicationComponent -> portRequiredSet
            .addAll(applicationComponent.getRequiredPorts()));
        portRequiredSet.forEach(portRequired -> {
            if (portRequired.communication() == null) {
                validationMessages.add(ValidationMessage.of(String
                    .format("PortRequired %s is missing mandatory communication entity.",
                        portRequired), ValidationMessage.Type.ERROR));
            }
        });
        return validationMessages;
    }
}
