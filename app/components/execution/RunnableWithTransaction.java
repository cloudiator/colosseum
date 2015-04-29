package components.execution;

import play.db.jpa.JPA;

/**
 * Created by daniel on 27.04.15.
 */
public class RunnableWithTransaction implements Runnable {

    private final Runnable runnable;

    public RunnableWithTransaction(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override public void run() {
        JPA.withTransaction(runnable::run);
    }


}
