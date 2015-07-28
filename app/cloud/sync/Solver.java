package cloud.sync;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import components.execution.Loop;
import components.execution.SimpleBlockingQueue;
import play.db.jpa.Transactional;

/**
 * Created by daniel on 05.05.15.
 */
public class Solver implements Runnable {

    private final SolutionDatabase solutionDatabase;
    private final SimpleBlockingQueue<Problem> problemQueue;

    @Inject public Solver(SolutionDatabase solutionDatabase,
        @Named(value = "problemQueue") SimpleBlockingQueue<Problem> problemQueue) {
        this.solutionDatabase = solutionDatabase;
        this.problemQueue = problemQueue;
    }

    @Transactional @Loop @Override public void run() {

        Problem problemToSolve = null;
        try {
            problemToSolve = this.problemQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (problemToSolve != null) {
            try {
                final Solution solution = this.solutionDatabase.getSolution(problemToSolve);
                solution.applyTo(problemToSolve);
            } catch (SolutionNotFoundException e) {
                throw new IllegalStateException(e);
            } catch (SolutionException e) {
                this.problemQueue.add(problemToSolve);
            }
        }
    }
}
