package cloud.sync.solutions;

import cloud.sync.Problem;
import cloud.sync.Solution;
import cloud.sync.SolutionException;
import cloud.sync.problems.VirtualMachineProblems;
import com.google.inject.Inject;
import models.VirtualMachine;
import models.service.VirtualMachineModelService;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 22.11.16.
 */
public class RemoveEmptyVirtualMachine implements Solution {

    private final VirtualMachineModelService virtualMachineModelService;

    @Inject
    public RemoveEmptyVirtualMachine(VirtualMachineModelService virtualMachineModelService) {
        checkNotNull(virtualMachineModelService, "virtualMachineModelService is null.");
        this.virtualMachineModelService = virtualMachineModelService;
    }

    @Override
    public boolean isSolutionFor(Problem problem) {
        return problem instanceof VirtualMachineProblems.VirtualMachineIsSpare;
    }

    @Override
    public void applyTo(Problem problem) throws SolutionException {
        checkState(isSolutionFor(problem));

        final VirtualMachine vm = ((VirtualMachineProblems.VirtualMachineIsSpare) problem).getResource();

        final VirtualMachine inDatabase = virtualMachineModelService.getById(vm.getId());

        if (inDatabase == null) {
            return;
        }

        virtualMachineModelService.delete(inDatabase);
    }
}
