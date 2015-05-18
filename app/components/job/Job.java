package components.job;

import components.execution.Prioritized;

/**
 * Created by daniel on 08.05.15.
 */
public interface Job extends Prioritized {

    String getResourceUuid();

    JobState state();

    void state(JobState jobState);

    void execute() throws JobException;

}
