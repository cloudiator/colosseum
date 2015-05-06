package cloud.sync;

import com.google.inject.Singleton;

import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by daniel on 04.05.15.
 */
@Singleton public class DefaultProblemQueue implements ProblemQueue {

    private BlockingQueue<Problem> queue;
    private HashSet<Problem> set;

    public DefaultProblemQueue() {
        this.queue = new PriorityBlockingQueue<>(10,
            (first, second) -> Integer.compare(first.getPriority(), second.getPriority()));
        this.set = new HashSet<>();

    }

    @Override public void add(Problem problem) {
        if (this.set.add(problem)) {
            this.queue.add(problem);
        }
    }

    @Override public Problem take() throws InterruptedException {
        Problem problem = this.queue.take();
        this.set.remove(problem);
        return problem;
    }
}
