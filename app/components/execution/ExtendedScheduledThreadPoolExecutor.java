package components.execution;

import play.Logger;

import java.util.concurrent.*;

/**
 * Created by daniel on 05.05.15.
 */
public class ExtendedScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {
    public ExtendedScheduledThreadPoolExecutor(int corePoolSize) {
        super(corePoolSize);
    }

    public ExtendedScheduledThreadPoolExecutor(int corePoolSize, ThreadFactory threadFactory) {
        super(corePoolSize, threadFactory);
    }

    public ExtendedScheduledThreadPoolExecutor(int corePoolSize, RejectedExecutionHandler handler) {
        super(corePoolSize, handler);
    }

    public ExtendedScheduledThreadPoolExecutor(int corePoolSize, ThreadFactory threadFactory,
        RejectedExecutionHandler handler) {
        super(corePoolSize, threadFactory, handler);
    }

    @Override protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t == null && r instanceof Future<?>) {
            try {
                if (((Future) r).isDone() && !((Future) r).isCancelled()) {
                    ((Future) r).get();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                t = e.getCause();
            }
        }
        if (t != null) {
            Logger.error("Uncaught exception occurred during the execution of task.", t);
        }
    }
}
