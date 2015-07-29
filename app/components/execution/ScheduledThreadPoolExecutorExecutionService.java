package components.execution;

import com.google.inject.Singleton;
import play.Logger;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by daniel on 17.04.15.
 */
@Singleton class ScheduledThreadPoolExecutorExecutionService implements ExecutionService {

    private final ScheduledExecutorService scheduledExecutorService;

    public ScheduledThreadPoolExecutorExecutionService() {
        scheduledExecutorService = new ExtendedScheduledThreadPoolExecutor(10);
    }

    @Override public void schedule(Schedulable schedulable) {
        Logger.debug(String
            .format("Scheduling %s with initial delay of %s and period of %s %s", schedulable,
                schedulable.delay(), schedulable.period(), schedulable.timeUnit()));
        this.scheduledExecutorService
            .scheduleAtFixedRate(schedulable, schedulable.delay(), schedulable.period(),
                schedulable.timeUnit());
    }

    @Override public void execute(Runnable runnable) {
        Logger.debug("Executing " + runnable);
        this.scheduledExecutorService.execute(runnable);
    }
}
