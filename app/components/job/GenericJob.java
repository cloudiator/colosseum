package components.job;

import cloud.colosseum.ColosseumComputeService;
import models.generic.Model;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 08.05.15.
 */
public abstract class GenericJob<T extends Model> implements Job {

    private final String resourceUuid;
    private final ModelService<T> modelService;
    private final ColosseumComputeService colosseumComputeService;
    private JobState jobState;

    public GenericJob(T t, ModelService<T> modelService,
        ColosseumComputeService colosseumComputeService) {
        this.colosseumComputeService = colosseumComputeService;
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
        T t = null;
        /**

         * todo: find a better way for waiting for the database
         */
        while (t == null) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println(resourceUuid);
            t = this.modelService.getByUuid(resourceUuid);
        }
        this.doWork(t, modelService, colosseumComputeService);
        this.modelService.save(t);
    }

    protected abstract void doWork(T t, ModelService<T> modelService,
        ColosseumComputeService computeService) throws JobException;
}
