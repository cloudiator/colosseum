package cloud.sync;

import com.google.inject.ImplementedBy;

/**
 * Created by daniel on 04.05.15.
 */
@ImplementedBy(DefaultSolutionDatabase.class) public interface SolutionDatabase {

    Solution getSolution(Problem problem) throws SolutionNotFoundException;
}
