package components.execution;

import com.google.inject.Singleton;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by daniel on 04.05.15.
 */
@Singleton public class DefaultPriorityQueue<T extends Prioritized> implements PriorityQueue<T> {

    private BlockingQueue<T> queue;

    public DefaultPriorityQueue() {
        this.queue = new PriorityBlockingQueue<>(10,
            (first, second) -> Integer.compare(first.getPriority(), second.getPriority()));
    }

    @Override public void add(T t) {
        this.queue.add(t);
    }

    @Override public T take() throws InterruptedException {
        return this.queue.take();
    }
}
