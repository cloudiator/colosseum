package components.execution;

import java.util.concurrent.TimeUnit;

/**
 * Created by daniel on 24.07.15.
 */
public interface Schedulable extends Runnable {

    long period();

    long delay();

    TimeUnit timeUnit();

}
