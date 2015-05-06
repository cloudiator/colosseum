package components.execution;

/**
 * Created by daniel on 06.05.15.
 */
public class Loop implements Runnable {

    private final Runnable runnable;

    public Loop(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            runnable.run();
        }
    }
}
