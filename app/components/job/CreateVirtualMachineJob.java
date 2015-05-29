package components.job;

import cloud.util.CloudScopedId;
import cloud.ColosseumVirtualMachineTemplate;
import cloud.ColosseumVirtualMachineTemplateBuilder;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import models.IpAddress;
import models.IpType;
import models.VirtualMachine;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 08.05.15.
 */
public class CreateVirtualMachineJob extends GenericJob<VirtualMachine> {

    public CreateVirtualMachineJob(VirtualMachine virtualMachine,
        ModelService<VirtualMachine> modelService, ComputeService computeService) {
        super(virtualMachine, modelService, computeService);
    }

    @Override
    protected void doWork(VirtualMachine virtualMachine, ModelService<VirtualMachine> modelService,
        ComputeService computeService) {
        ColosseumVirtualMachineTemplateBuilder builder = ColosseumVirtualMachineTemplate.builder();
        de.uniulm.omi.cloudiator.sword.api.domain.VirtualMachine cloudVirtualMachine =
            computeService
                .createVirtualMachine(builder.virtualMachineModel(virtualMachine).build());
        final CloudScopedId cloudScopedId =
            CloudScopedId.of(cloudVirtualMachine.id());
        virtualMachine.setCloudUuid(cloudScopedId.baseId());
        for (String ip : cloudVirtualMachine.privateAddresses()) {
            virtualMachine.getIpAddresses().add(new IpAddress(virtualMachine, ip, IpType.PRIVATE));
        }
        for (String ip : cloudVirtualMachine.publicAddresses()) {
            virtualMachine.getIpAddresses().add(new IpAddress(virtualMachine, ip, IpType.PUBLIC));
        }
    }
}
