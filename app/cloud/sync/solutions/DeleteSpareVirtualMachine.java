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

import cloud.CloudService;
import cloud.resources.VirtualMachineInLocation;
import cloud.sync.Problem;
import cloud.sync.Solution;
import cloud.sync.SolutionException;
import cloud.sync.problems.VirtualMachineProblems;
import com.google.inject.Inject;

import java.util.stream.StreamSupport;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 11.11.16.
 */
public class DeleteSpareVirtualMachine implements Solution {

    private final CloudService cloudService;

    @Inject public DeleteSpareVirtualMachine(CloudService cloudService) {
        checkNotNull(cloudService);
        this.cloudService = cloudService;
    }

    @Override public boolean isSolutionFor(Problem problem) {
        return problem instanceof VirtualMachineProblems.VirtualMachineNotInDatabase;
    }

    @Override public void applyTo(Problem problem) throws SolutionException {
        checkState(isSolutionFor(problem));
        VirtualMachineInLocation virtualMachineInLocation =
            ((VirtualMachineProblems.VirtualMachineNotInDatabase) problem).getVirtualMachine();

        final boolean present = StreamSupport
            .stream(cloudService.discoveryService().listVirtualMachines().spliterator(), false)
            .filter(remoteVirtualMachine -> remoteVirtualMachine.id()
                .equals(virtualMachineInLocation.id())).findAny().isPresent();

        if (!present) {
            return;
        }
        cloudService.computeService().deleteVirtualMachine(virtualMachineInLocation);
    }
}
