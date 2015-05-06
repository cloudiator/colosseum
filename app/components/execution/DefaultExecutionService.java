package components.execution;

import play.Logger;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by daniel on 17.04.15.
 */
public class DefaultExecutionService implements ExecutionService {

    private final ScheduledExecutorService scheduledExecutorService;

    public DefaultExecutionService() {
        scheduledExecutorService = new ExtendedScheduledThreadPoolExecutor(10);
    }

    @Override public void scheduleAtFixedRate(Runnable runnable, long period, TimeUnit timeUnit) {
        Logger.debug("Scheduling " + runnable + " with period of " + period + " " + timeUnit);
        this.scheduledExecutorService.scheduleAtFixedRate(runnable, 0, period, timeUnit);
    }

    @Override public void execute(Runnable runnable) {
        Logger.debug("Executing " + runnable);
        this.scheduledExecutorService.execute(runnable);
    }

    @Override public void executeInLoop(Runnable runnable) {
        this.scheduledExecutorService.execute(new Loop(runnable));
    }
}
