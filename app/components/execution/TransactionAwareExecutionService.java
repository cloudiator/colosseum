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

    private Runnable decorateRunnableIfTransaction(Runnable runnable) {
        try {
            if (runnable.getClass().getMethod("run").isAnnotationPresent(Transactional.class)) {
                return new RunWithTransaction(runnable);
            }
        } catch (NoSuchMethodException shouldNeverOccur) {
            throw new AssertionError("Runnable without run method", shouldNeverOccur);
        }
        return runnable;
    }

    @Override public void scheduleAtFixedRate(Runnable runnable, long delay, TimeUnit timeUnit) {
        this.executionService
            .scheduleAtFixedRate(this.decorateRunnableIfTransaction(runnable), delay, timeUnit);
    }

    @Override public void execute(Runnable runnable) {
        this.executionService.execute(this.decorateRunnableIfTransaction(runnable));
    }

    @Override public void executeInLoop(Runnable runnable) {
        this.executionService.executeInLoop(this.decorateRunnableIfTransaction(runnable));
    }
}
