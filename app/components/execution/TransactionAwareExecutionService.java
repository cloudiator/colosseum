package components.execution;

import play.db.jpa.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * Created by daniel on 27.04.15.
 */
public class TransactionAwareExecutionService implements ExecutionService {

    private final ExecutionService executionService;

    public TransactionAwareExecutionService(ExecutionService executionService) {
        this.executionService = executionService;
    }

    private Runnable wrapRunnable(Runnable runnable) {
        if (runnable.getClass().isAnnotationPresent(Transactional.class)) {
            //wrap in runnable with transaction
            runnable = new RunnableWithTransaction(runnable);
        }
        return runnable;
    }

    @Override public void schedule(Runnable runnable, long delay, TimeUnit timeUnit) {
        this.executionService.schedule(this.wrapRunnable(runnable), delay, timeUnit);
    }
}
