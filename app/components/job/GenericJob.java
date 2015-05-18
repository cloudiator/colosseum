package components.job;

import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import models.generic.Model;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 08.05.15.
 */
public abstract class GenericJob<T extends Model> implements Job {

    private final String resourceUuid;
    private final ModelService<T> modelService;
    private final ComputeService computeService;
    private JobState jobState;

    public GenericJob(T t, ModelService<T> modelService, ComputeService computeService) {
        this.computeService = computeService;
        this.resourceUuid = t.getUuid();
        this.modelService = modelService;
    }

    @Override public String getResourceUuid() {
        return resourceUuid;
    }

    @Override public JobState state() {
        return jobState;
    }

    @Override public void state(JobState jobState) {
        this.jobState = jobState;
    }

    @Override public int getPriority() {
        return Priority.HIGH;
    }

    @Override public void execute() throws JobException {
        T t = this.modelService.getByUuid(resourceUuid);
        this.doWork(t, modelService, computeService);
        this.modelService.save(t);
    }

    protected abstract void doWork(T t, ModelService<T> modelService, ComputeService computeService)
        throws JobException;
}
