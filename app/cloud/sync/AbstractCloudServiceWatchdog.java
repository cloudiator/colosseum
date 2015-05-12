package cloud.sync;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import components.execution.SimpleBlockingQueue;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import play.db.jpa.Transactional;

import static com.google.common.base.Preconditions.checkNotNull;



/**
 * Created by daniel on 27.04.15.
 */
public abstract class AbstractCloudServiceWatchdog implements Runnable {

    private final ComputeService computeService;
    private final SimpleBlockingQueue<Problem> problemQueue;

    @Inject protected AbstractCloudServiceWatchdog(ComputeService computeService,
        @Named(value = "problemQueue") SimpleBlockingQueue<Problem> simpleBlockingQueue) {
        checkNotNull(computeService);
        checkNotNull(simpleBlockingQueue);
        this.computeService = computeService;
        this.problemQueue = simpleBlockingQueue;
    }

    @Transactional @Override public void run() {
        watch(this.computeService);
    }

    protected abstract void watch(ComputeService computeService);

    protected void report(Problem problem) {
        this.problemQueue.add(problem);
    }
}
