package components.execution;

import java.util.HashSet;

/**
 * Created by daniel on 08.05.15.
 */
public class UniqueQueue<T> implements SimpleBlockingQueue<T> {

    private final HashSet<T> set;
    private final SimpleBlockingQueue<T> queue;

    public UniqueQueue(SimpleBlockingQueue<T> queue) {
        set = new HashSet<>();
        this.queue = queue;
    }

    @Override public void add(T t) {
        if (this.set.add(t)) {
            this.queue.add(t);
        }
    }

    @Override public T take() throws InterruptedException {
        T t = this.queue.take();
        this.set.remove(t);
        return t;
    }

}
