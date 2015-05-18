package components.job;

import com.google.inject.Singleton;
import components.execution.DefaultPriorityQueue;
import components.execution.PriorityQueue;
import components.execution.SimpleBlockingQueue;

/**
 * Created by daniel on 07.05.15.
 */
@Singleton public class JobQueue implements PriorityQueue<Job> {

    private SimpleBlockingQueue<Job> jobSimpleBlockingQueue;

    public JobQueue() {
        this.jobSimpleBlockingQueue = new DefaultPriorityQueue<>();
    }

    @Override public void add(Job t) {
        jobSimpleBlockingQueue.add(t);
    }

    @Override public Job take() throws InterruptedException {
        return jobSimpleBlockingQueue.take();
    }
}
