package components.execution;

import play.Logger;
import play.db.jpa.JPA;
import play.libs.F;

/**
 * Created by daniel on 27.04.15.
 */
public class RunWithTransaction implements Runnable {

    private final Runnable runnable;
    private final boolean readOnly;

    public RunWithTransaction(Runnable runnable) {
        this.runnable = runnable;
        this.readOnly = false;
    }

    public RunWithTransaction(Runnable runnable, boolean readOnly) {
        this.runnable = runnable;
        this.readOnly = readOnly;
    }

    @Override public void run() {
        Logger.debug("Running " + runnable + " in transaction context.");
        try {
            JPA.withTransaction("default", readOnly, () -> {
                runnable.run();
                return null;
            });
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
        Logger.debug("Finished " + runnable + " in transaction context.");
    }


}
