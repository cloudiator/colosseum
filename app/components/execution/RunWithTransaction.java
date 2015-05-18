package components.execution;

import play.Logger;
import play.db.jpa.JPA;

/**
 * Created by daniel on 27.04.15.
 */
public class RunWithTransaction implements Runnable {

    private final Runnable runnable;

    public RunWithTransaction(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override public void run() {
        Logger.debug("Running " + runnable + " in transaction context.");
        JPA.withTransaction(runnable::run);
        Logger.debug("Finished " + runnable + " in transaction context.");
    }


}
