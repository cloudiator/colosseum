package cloud.sync;

import com.google.inject.Inject;

import java.util.Set;

/**
 * Created by daniel on 04.05.15.
 */
public class DefaultSolutionDatabase implements SolutionDatabase {



    private final Set<Solution> availableSolutions;

    @Inject public DefaultSolutionDatabase(Set<Solution> solutions) {
        this.availableSolutions = solutions;
    }

    @Override public Solution getSolution(Problem problem) throws SolutionNotFoundException {
        for (Solution solution : availableSolutions) {
            if (solution.isSolutionFor(problem))
                return solution;
        }
        throw new SolutionNotFoundException();
    }


}
