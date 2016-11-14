package cloud.sync.detectors;

import cloud.sync.Problem;
import cloud.sync.ProblemDetector;
import cloud.sync.problems.VirtualMachineProblems;
import models.VirtualMachine;
import models.generic.RemoteState;

import java.util.Optional;

/**
 * Created by daniel on 12.11.16.
 */
public class VirtualMachineInErrorStateDetector implements ProblemDetector<VirtualMachine> {

    @Override
    public Optional<Problem<VirtualMachine>> apply(VirtualMachine virtualMachine) {


        if (RemoteState.ERROR.equals(virtualMachine.getRemoteState())) {
            return new Optional.of(new VirtualMachineProblems.VirtualMachineInErrorState(virtualMachine));
        }

        return Optional.empty();

    }
}
