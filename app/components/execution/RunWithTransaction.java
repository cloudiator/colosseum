package components.execution;

import play.Logger;
import play.db.jpa.JPA;
import play.libs.F;

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
        JPA.withTransaction(new F.Callback0() {
            @Override public void invoke() throws Throwable {
                runnable.run();
            }
        });
        Logger.debug("Finished " + runnable + " in transaction context.");
    }


}
