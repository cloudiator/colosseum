package components.execution;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by daniel on 17.04.15.
 */
public class DefaultExecutionService implements ExecutionService {

    private final ScheduledExecutorService scheduledExecutorService;

    public DefaultExecutionService() {
        scheduledExecutorService = new ScheduledThreadPoolExecutor(10);
    }

    @Override public void schedule(Runnable runnable, long delay, TimeUnit timeUnit) {
        this.scheduledExecutorService.schedule(runnable, delay, timeUnit);
    }


}
