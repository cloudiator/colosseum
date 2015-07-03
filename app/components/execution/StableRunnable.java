package components.execution;

import play.Logger;

/**
 * Created by daniel on 03.07.15.
 */
public class StableRunnable implements Runnable {

    private final Runnable runnable;

    public StableRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override public void run() {
        try {
            runnable.run();
        } catch (Exception e) {
            Logger.error("StableRunner encountered error", e);
        }
    }
}
