/**
 * Created by daniel on 17.04.15.
 */
package components.execution;

import java.util.concurrent.TimeUnit;

public interface ExecutionService {

    void schedule(Runnable runnable, long delay, TimeUnit timeUnit);

}
