package cloud.colosseum;

import cloud.resources.VirtualMachineInLocation;

/**
 * Created by daniel on 12.06.15.
 */
public interface ColosseumComputeService {

    VirtualMachineInLocation createVirtualMachine(
        ColosseumVirtualMachineTemplate virtualMachineTemplate);
}
