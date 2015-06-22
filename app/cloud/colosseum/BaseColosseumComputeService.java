package cloud.colosseum;

import cloud.ComputeServiceRegistry;
import cloud.resources.VirtualMachineInLocation;

/**
 * Created by daniel on 21.06.15.
 */
public class BaseColosseumComputeService implements ColosseumComputeService {

    private final ComputeServiceRegistry computeServices;

    public BaseColosseumComputeService(ComputeServiceRegistry computeServices) {
        this.computeServices = computeServices;
    }

    @Override public VirtualMachineInLocation createVirtualMachine(
        ColosseumVirtualMachineTemplate virtualMachineTemplate) {
        return this.computeServices
            .getComputeService(virtualMachineTemplate.getCloudCredentialUuid())
            .createVirtualMachine(virtualMachineTemplate);
    }
}
