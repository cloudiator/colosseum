/**
 * Created by daniel on 17.04.15.
 */
package components.execution;

interface ExecutionService {

    void schedule(Schedulable schedulable);

    void execute(Runnable runnable);
}
