package components.job.config;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import components.execution.SimpleBlockingQueue;
import components.job.BaseJobService;
import components.job.Job;
import components.job.JobQueue;
import components.job.JobService;

/**
 * Created by daniel on 12.05.15.
 */
public class JobModule extends AbstractModule {

    @Override protected void configure() {
        bind(new TypeLiteral<SimpleBlockingQueue<Job>>() {
        }).annotatedWith(Names.named("jobQueue")).to(JobQueue.class);
        bind(JobService.class).to(BaseJobService.class);
    }
}
