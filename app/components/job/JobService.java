package components.job;

import models.VirtualMachine;

/**
 * Created by daniel on 03.07.15.
 */
public interface JobService {

    void newVirtualMachineJob(VirtualMachine virtualMachine);
}
