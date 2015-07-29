package components.execution;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 24.07.15.
 */
@Singleton public class Init {

    @Inject public Init(ExecutionService executionService, Set<Runnable> runnables,
        Set<Schedulable> schedulables) {

        checkNotNull(executionService);
        checkNotNull(runnables);
        checkNotNull(schedulables);

        for (Runnable runnable : runnables) {
            executionService.execute(runnable);
        }
        for (Schedulable schedulable : schedulables) {
            executionService.schedule(schedulable);
        }
    }
}
