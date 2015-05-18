package cloud.sync;

/**
 * Created by daniel on 04.05.15.
 */
public interface Solution {

    boolean isSolutionFor(Problem problem);

    void applyTo(Problem problem) throws SolutionException;

}
