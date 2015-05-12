package components.job;

import components.execution.Prioritized;

/**
 * Created by daniel on 08.05.15.
 */
interface Job extends Prioritized {

    String getResourceUuid();

    JobState getState();

    void execute();

}
