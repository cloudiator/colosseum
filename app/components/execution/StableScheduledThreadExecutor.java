package components.execution;

import play.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Created by daniel on 27.08.15.
 */
public class StableScheduledThreadExecutor implements ExecutionService {

    private final static Logger.ALogger LOGGER = play.Logger.of("colosseum.execution");

    private final ExecutionService executionService;

    public StableScheduledThreadExecutor(ExecutionService executionService) {
        this.executionService = executionService;
    }

    private Schedulable wrapSchedulableIfNeeded(Schedulable schedulable) {

        if (schedulable.getClass().isAnnotationPresent(Stable.class)) {
            schedulable = new StableSchedulable(schedulable);
        }
        return schedulable;
    }

    @Override public void schedule(Schedulable schedulable) {
        executionService.schedule(wrapSchedulableIfNeeded(schedulable));
    }

    @Override public void execute(Runnable runnable) {
        executionService.execute(runnable);
    }

    private static class StableSchedulable implements Schedulable {

        private final Schedulable delegate;

        private StableSchedulable(Schedulable delegate) {
            this.delegate = delegate;
        }

        @Override public long period() {
            return delegate.period();
        }

        @Override public long delay() {
            return delegate.delay();
        }

        @Override public TimeUnit timeUnit() {
            return delegate.timeUnit();
        }

        @Override public void run() {
            try {
                LOGGER.debug(
                    String.format("Running %s in stable context", delegate.getClass().getName()));
                delegate.run();
            } catch (Exception e) {
                LOGGER.error(
                    "Encountered error in stable schedulable, catched to allow further execution.",
                    e);
            }
        }
    }

}
