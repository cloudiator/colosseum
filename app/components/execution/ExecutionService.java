/**
 * Created by daniel on 17.04.15.
 */
package components.execution;

import java.util.concurrent.TimeUnit;

public interface ExecutionService {

    void scheduleAtFixedRate(Runnable runnable, long delay, TimeUnit timeUnit);

    void execute(Runnable runnable);

    void executeInLoop(Runnable runnable);
}
