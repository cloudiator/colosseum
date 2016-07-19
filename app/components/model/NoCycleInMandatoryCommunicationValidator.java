package components.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Set;

import models.Application;

/**
 * Created by daniel on 19.06.16.
 */
public class NoCycleInMandatoryCommunicationValidator implements ModelValidator<Application> {
    
    @Override public Set<ValidationMessage> validate(Application application) {
        final ApplicationTypeGraph graph = ApplicationTypeGraph.of(application);
        if (graph.hasCycle()) {
            return Collections.singleton(ValidationMessage.of(String.format(
                "Found at least one loop in the communication graph. %s are participants of the loop.",
                StringUtils.join(graph.cycles(), ",")), ValidationMessage.Type.ERROR));
        }
        return Collections.emptySet();
    }
}
