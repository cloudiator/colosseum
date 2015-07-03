package components.job;

import cloud.CloudService;
import cloud.colosseum.ColosseumComputeService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import components.execution.SimpleBlockingQueue;
import models.VirtualMachine;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 03.07.15.
 */
@Singleton public class BaseJobService implements JobService {

    private final ModelService<VirtualMachine> virtualMachineModelService;
    private final ColosseumComputeService colosseumComputeService;
    private final SimpleBlockingQueue<Job> jobQueue;

    @Inject public BaseJobService(ModelService<VirtualMachine> virtualMachineModelService,
        CloudService cloudService, @Named("jobQueue") SimpleBlockingQueue<Job> jobQueue) {
        this.virtualMachineModelService = virtualMachineModelService;
        this.colosseumComputeService = cloudService.computeService();
        this.jobQueue = jobQueue;
    }

    @Override public void newVirtualMachineJob(VirtualMachine virtualMachine) {
        this.jobQueue.add(new CreateVirtualMachineJob(virtualMachine, virtualMachineModelService,
            colosseumComputeService));
    }

}
