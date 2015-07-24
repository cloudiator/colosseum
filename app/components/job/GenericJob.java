package components.job;

import cloud.colosseum.ColosseumComputeService;
import models.Tenant;
import models.generic.Model;
import models.service.ModelService;

/**
 * Created by daniel on 08.05.15.
 */
public abstract class GenericJob<T extends Model> implements Job {

    private final String resourceUuid;
    private final ModelService<T> modelService;
    private final ColosseumComputeService colosseumComputeService;
    private JobState jobState;
    private final Tenant tenant;
    private final ModelService<Tenant> tenantModelService;

    public GenericJob(T t, ModelService<T> modelService, ModelService<Tenant> tentantModelService,
        ColosseumComputeService colosseumComputeService, Tenant tenant) {
        this.colosseumComputeService = colosseumComputeService;
        this.resourceUuid = t.getUuid();
        this.modelService = modelService;
        this.tenantModelService = tentantModelService;
        this.tenant = tenant;
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

    @Override public final void execute() throws JobException {
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
        this.doWork(t, modelService, colosseumComputeService,
            tenantModelService.getById(tenant.getId()));
        this.modelService.save(t);
    }

    protected abstract void doWork(T t, ModelService<T> modelService,
        ColosseumComputeService computeService, Tenant tenant) throws JobException;
}
