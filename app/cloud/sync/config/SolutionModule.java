package cloud.sync.config;

import cloud.sync.Solution;
import cloud.sync.solutions.CreateLocationInDatabase;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

/**
 * Created by daniel on 05.05.15.
 */
public class SolutionModule extends AbstractModule {

    @Override protected void configure() {
        Multibinder<Solution> solutionBinder = Multibinder.newSetBinder(binder(), Solution.class);
        solutionBinder.addBinding().to(CreateLocationInDatabase.class);
    }

}
