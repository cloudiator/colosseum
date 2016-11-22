package cloud.sync.detectors;

import cloud.sync.Problem;
import cloud.sync.ProblemDetector;
import cloud.sync.problems.VirtualMachineProblems;
import models.VirtualMachine;
import models.generic.RemoteState;
import play.Logger;
import util.logging.Loggers;

import java.util.Optional;

/**
 * Created by daniel on 22.11.16.
 */
public class EmptyMachineDetector implements ProblemDetector<VirtualMachine> {

    private final static Logger.ALogger LOGGER = Loggers.of(Loggers.CLOUD_SYNC);

    @Override
    public Optional<Problem<VirtualMachine>> apply(VirtualMachine virtualMachine) {
        if (!RemoteState.OK.equals(virtualMachine.getRemoteState())) {
            LOGGER.debug(String.format("Skipping %s on virtual machine %s as it is not in %s state", this, virtualMachine, RemoteState.OK));
        }

        if (virtualMachine.instances().isEmpty()) {
            return Optional.of(new VirtualMachineProblems.VirtualMachineIsSpare(virtualMachine));
        }

        return Optional.empty();
    }
}
