package components.execution;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import com.google.inject.multibindings.Multibinder;
import play.db.jpa.Transactional;

/**
 * Created by daniel on 24.07.15.
 */
public class ExecutionModule extends AbstractModule {

    @Override protected void configure() {

        bindInterceptor(Matchers.subclassesOf(Runnable.class), Matchers.annotatedWith(Stable.class),
            new StableRunnableInterceptor());
        bindInterceptor(Matchers.subclassesOf(Runnable.class), Matchers.annotatedWith(Loop.class),
            new LoopRunnableInterceptor());
        bindInterceptor(Matchers.subclassesOf(Runnable.class),
            Matchers.annotatedWith(Transactional.class), new TransactionalRunnableInterceptor());


        bind(ExecutionService.class).to(ScheduledThreadPoolExecutorExecutionService.class);
        bind(Init.class).asEagerSingleton();


        Multibinder<Runnable> runnableMultibinder =
            Multibinder.newSetBinder(binder(), Runnable.class);
        Multibinder<Schedulable> schedulableMultibinder =
            Multibinder.newSetBinder(binder(), Schedulable.class);
    }
}
