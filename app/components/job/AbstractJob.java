package components.job;

import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import models.generic.Model;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 08.05.15.
 */
public abstract class AbstractJob<T extends Model> implements Job {

    private final String resourceUuid;
    private final ModelService<T> modelService;
    private final ComputeService computeService;

    public AbstractJob(T t, ModelService<T> modelService, ComputeService computeService) {
        this.computeService = computeService;
        this.resourceUuid = t.getUuid();
        this.modelService = modelService;
    }

    @Override public String getResourceUuid() {
        return resourceUuid;
    }

    @Override public JobState getState() {
        return null;
    }

    @Override public int getPriority() {
        return Priority.HIGH;
    }

    @Override public void execute() {
        T t = this.modelService.getByUuid(resourceUuid);
        this.doWork(t, this.computeService);
        this.modelService.save(t);
    }

    protected abstract void doWork(T t, ComputeService computeService);
}
