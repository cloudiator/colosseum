package components.execution;

/**
 * Created by daniel on 08.05.15.
 */
public interface SimpleBlockingQueue<T> {

    void add(T t);

    T take() throws InterruptedException;
}
