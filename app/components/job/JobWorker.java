package components.job;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import components.execution.SimpleBlockingQueue;
import components.execution.StableRunnable;
import play.Logger;
import play.db.jpa.Transactional;


/**
 * Created by daniel on 12.05.15.
 */

public class JobWorker implements Runnable {

    private final SimpleBlockingQueue<Job> jobQueue;

    @Inject public JobWorker(@Named("jobQueue") SimpleBlockingQueue<Job> jobQueue) {
        this.jobQueue = jobQueue;
    }

    @Transactional @Override public void run() {
        new StableRunnable(() -> {
            try {
                Job job = jobQueue.take();
                job.execute();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (JobException e) {
                Logger.error("Job Execution got error", e);
            }
        }).run();
    }
}
