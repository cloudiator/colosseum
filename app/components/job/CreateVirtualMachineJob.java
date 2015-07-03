package components.job;

import cloud.colosseum.BaseColosseumVirtualMachineTemplate;
import cloud.colosseum.ColosseumComputeService;
import cloud.colosseum.ColosseumVirtualMachineTemplateBuilder;
import cloud.resources.VirtualMachineInLocation;
import com.google.common.base.Optional;
import de.uniulm.omi.cloudiator.sword.api.exceptions.PublicIpException;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;
import models.IpAddress;
import models.IpType;
import models.VirtualMachine;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 08.05.15.
 */
public class CreateVirtualMachineJob extends GenericJob<VirtualMachine> {

    public CreateVirtualMachineJob(VirtualMachine virtualMachine,
        ModelService<VirtualMachine> modelService,
        ColosseumComputeService colosseumComputeService) {
        super(virtualMachine, modelService, colosseumComputeService);
    }

    @Override
    protected void doWork(VirtualMachine virtualMachine, ModelService<VirtualMachine> modelService,
        ColosseumComputeService computeService) throws JobException {
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

        modelService.save(virtualMachine);

        if (virtualMachine.getPublicIpAddress() == null) {
            final Optional<PublicIpService> publicIpService =
                computeService.getPublicIpService(virtualMachine.getCloud().getUuid());
            if (publicIpService.isPresent()) {
                try {
                    final String publicIp =
                        publicIpService.get().addPublicIp(virtualMachine.getCloudProviderId());
                    virtualMachine.getIpAddresses()
                        .add(new IpAddress(virtualMachine, publicIp, IpType.PUBLIC));
                } catch (PublicIpException e) {
                    throw new JobException(e);
                }
            } else {
                throw new JobException(
                    "VirtualMachine started without public IP and IpService is not available.");
            }
        }
    }
}
