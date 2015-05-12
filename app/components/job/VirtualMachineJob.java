package components.job;

import cloud.CloudCredentialLocationId;
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
public class VirtualMachineJob extends AbstractJob<VirtualMachine> {

    public VirtualMachineJob(VirtualMachine virtualMachine,
        ModelService<VirtualMachine> modelService, ComputeService computeService) {
        super(virtualMachine, modelService, computeService);
    }

    @Override protected void doWork(VirtualMachine virtualMachine, ComputeService computeService) {
        ColosseumVirtualMachineTemplateBuilder builder = ColosseumVirtualMachineTemplate.builder();
        de.uniulm.omi.cloudiator.sword.api.domain.VirtualMachine cloudVirtualMachine =
            computeService
                .createVirtualMachine(builder.virtualMachineModel(virtualMachine).build());
        final CloudCredentialLocationId cloudCredentialLocationId =
            CloudCredentialLocationId.of(cloudVirtualMachine.id());
        virtualMachine.setCloudUuid(cloudCredentialLocationId.baseId());
        for (String ip : cloudVirtualMachine.privateAddresses()) {
            virtualMachine.getIpAddresses().add(new IpAddress(ip, IpType.PRIVATE));
        }
        for (String ip : cloudVirtualMachine.publicAddresses()) {
            virtualMachine.getIpAddresses().add(new IpAddress(ip, IpType.PUBLIC));
        }
    }
}
