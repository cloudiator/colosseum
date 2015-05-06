package cloud.sync;

import com.google.inject.Inject;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import play.db.jpa.Transactional;

import static com.google.common.base.Preconditions.checkNotNull;



/**
 * Created by daniel on 27.04.15.
 */
public abstract class AbstractCloudServiceWatchdog implements Runnable {

    private final ComputeService computeService;
    private final ProblemQueue problemQueue;

    @Inject protected AbstractCloudServiceWatchdog(ComputeService computeService,
        ProblemQueue problemQueue) {
        checkNotNull(computeService);
        checkNotNull(problemQueue);
        this.computeService = computeService;
        this.problemQueue = problemQueue;
    }

    @Transactional @Override public void run() {
        watch(this.computeService);
    }

    protected abstract void watch(ComputeService computeService);

    protected void report(Problem problem) {
        this.problemQueue.add(problem);
    }
}
