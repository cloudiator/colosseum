package cloud.sync;

import com.google.inject.ImplementedBy;

/**
 * Created by daniel on 04.05.15.
 */
@ImplementedBy(DefaultProblemQueue.class) public interface ProblemQueue {
    void add(Problem problem);

    Problem take() throws InterruptedException;
}
