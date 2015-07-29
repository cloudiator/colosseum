package cloud.sync.config;

import cloud.sync.Problem;
import cloud.sync.ProblemQueue;
import cloud.sync.Solution;
import cloud.sync.Solver;
import cloud.sync.solutions.*;
import cloud.sync.watchdogs.HardwareWatchdog;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import components.execution.Schedulable;
import components.execution.SimpleBlockingQueue;

/**
 * Created by daniel on 05.05.15.
 */
public class SolutionModule extends AbstractModule {

    @Override protected void configure() {
        this.bindSolutions();

        bind(new TypeLiteral<SimpleBlockingQueue<Problem>>() {
        }).annotatedWith(Names.named("problemQueue")).to(ProblemQueue.class);

        Multibinder<Schedulable> schedulableMultibinder =
            Multibinder.newSetBinder(binder(), Schedulable.class);
        schedulableMultibinder.addBinding().to(HardwareWatchdog.class);

        Multibinder<Runnable> runnableMultibinder =
            Multibinder.newSetBinder(binder(), Runnable.class);
        runnableMultibinder.addBinding().to(Solver.class);

    }

    private void bindSolutions() {
        Multibinder<Solution> solutionBinder = Multibinder.newSetBinder(binder(), Solution.class);
        solutionBinder.addBinding().to(CreateLocationInDatabase.class);
        solutionBinder.addBinding().to(ConnectLocationToCredential.class);
        solutionBinder.addBinding().to(CreateHardwareInDatabase.class);
        solutionBinder.addBinding().to(ConnectHardwareToCredential.class);
        solutionBinder.addBinding().to(ConnectHardwareToLocation.class);
        solutionBinder.addBinding().to(CreateImageInDatabase.class);
        solutionBinder.addBinding().to(ConnectImageToCredential.class);
        solutionBinder.addBinding().to(ConnectImageToLocation.class);
    }


}
