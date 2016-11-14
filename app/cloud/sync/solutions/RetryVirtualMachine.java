/*
 * Copyright (c) 2014-2016 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package cloud.sync.solutions;

import cloud.sync.Problem;
import cloud.sync.Solution;
import cloud.sync.SolutionException;
import cloud.sync.problems.VirtualMachineProblems;
import com.google.inject.Inject;
import components.job.JobService;
import models.VirtualMachine;
import models.generic.RemoteState;
import models.service.VirtualMachineModelService;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 14.11.16.
 */
public class RetryVirtualMachine implements Solution {

    private final JobService jobService;
    private final VirtualMachineModelService virtualMachineModelService;

    @Inject public RetryVirtualMachine(JobService jobService,
        VirtualMachineModelService virtualMachineModelService) {

        checkNotNull(virtualMachineModelService, "virtualMachineModelService is null.");

        this.virtualMachineModelService = virtualMachineModelService;

        checkNotNull(jobService, "jobService is null.");

        this.jobService = jobService;
    }

    @Override public boolean isSolutionFor(Problem problem) {
        return problem instanceof VirtualMachineProblems.VirtualMachineInErrorState;
    }

    @Override public void applyTo(Problem problem) throws SolutionException {
        checkState(isSolutionFor(problem));

        VirtualMachineProblems.VirtualMachineInErrorState virtualMachineInErrorState =
            (VirtualMachineProblems.VirtualMachineInErrorState) problem;

        VirtualMachine virtualMachine =
            virtualMachineModelService.getById(virtualMachineInErrorState.getResource().getId());

        if (virtualMachine == null) {
            throw new SolutionException("Virtual machine does not longer exist.");
        }

        if (!RemoteState.ERROR.equals(virtualMachine.getRemoteState())) {
            return;
        }

        checkState(virtualMachine.owner().isPresent(), "Owner of virtual machine is not present.");

        //todo: check if virtual machine has already an instance installed?

        //unbind existing virtual machine and remove ipAddresses
        virtualMachine.unbind();
        virtualMachineModelService.save(virtualMachine);

        jobService.newVirtualMachineJob(virtualMachine, virtualMachine.owner().get().getTenant());
    }
}
