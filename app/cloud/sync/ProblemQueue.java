package cloud.sync;

import com.google.inject.Singleton;
import components.execution.DefaultPriorityQueue;
import components.execution.SimpleBlockingQueue;
import components.execution.UniqueQueue;

/**
 * Created by daniel on 08.05.15.
 */
@Singleton public class ProblemQueue implements SimpleBlockingQueue<Problem> {

    private final SimpleBlockingQueue<Problem> simpleBlockingQueue;

    public ProblemQueue() {
        this.simpleBlockingQueue = new UniqueQueue<>(new DefaultPriorityQueue<>());
    }

    @Override public void add(Problem t) {
        this.simpleBlockingQueue.add(t);
    }

    @Override public Problem take() throws InterruptedException {
        return this.simpleBlockingQueue.take();
    }
}
