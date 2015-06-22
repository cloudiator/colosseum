package components.job;

import cloud.CloudService;
import cloud.colosseum.BaseColosseumVirtualMachineTemplate;
import cloud.colosseum.ColosseumComputeService;
import cloud.colosseum.ColosseumVirtualMachineTemplateBuilder;
import cloud.resources.VirtualMachineInLocation;
import models.IpAddress;
import models.IpType;
import models.VirtualMachine;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 08.05.15.
 */
public class CreateVirtualMachineJob extends GenericJob<VirtualMachine> {

    public CreateVirtualMachineJob(VirtualMachine virtualMachine,
        ModelService<VirtualMachine> modelService, CloudService cloudService) {
        super(virtualMachine, modelService, cloudService);
    }

    @Override
    protected void doWork(VirtualMachine virtualMachine, ModelService<VirtualMachine> modelService,
        ColosseumComputeService computeService) {
        ColosseumVirtualMachineTemplateBuilder builder =
            BaseColosseumVirtualMachineTemplate.builder();
        VirtualMachineInLocation cloudVirtualMachine = computeService
            .createVirtualMachine(builder.virtualMachineModel(virtualMachine).build());
        virtualMachine.setRemoteId(cloudVirtualMachine.id());
        for (String ip : cloudVirtualMachine.privateAddresses()) {
            virtualMachine.getIpAddresses().add(new IpAddress(virtualMachine, ip, IpType.PRIVATE));
        }
        for (String ip : cloudVirtualMachine.publicAddresses()) {
            virtualMachine.getIpAddresses().add(new IpAddress(virtualMachine, ip, IpType.PUBLIC));
        }
    }
}
