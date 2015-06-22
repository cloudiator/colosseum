package cloud.sync;

import cloud.CloudService;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import components.execution.SimpleBlockingQueue;
import play.db.jpa.Transactional;

import static com.google.common.base.Preconditions.checkNotNull;



/**
 * Created by daniel on 27.04.15.
 */
public abstract class AbstractCloudServiceWatchdog implements Runnable {

    private final CloudService cloudService;
    private final SimpleBlockingQueue<Problem> problemQueue;

    @Inject protected AbstractCloudServiceWatchdog(CloudService cloudService,
        @Named(value = "problemQueue") SimpleBlockingQueue<Problem> simpleBlockingQueue) {
        checkNotNull(cloudService);
        checkNotNull(simpleBlockingQueue);
        this.cloudService = cloudService;
        this.problemQueue = simpleBlockingQueue;
    }

    @Transactional @Override public void run() {
        watch(this.cloudService);
    }

    protected abstract void watch(CloudService cloudService);

    protected void report(Problem problem) {
        this.problemQueue.add(problem);
    }
}
