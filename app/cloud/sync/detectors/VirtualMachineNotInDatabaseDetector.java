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

package cloud.sync.detectors;

import cloud.resources.VirtualMachineInLocation;
import cloud.sync.Problem;
import cloud.sync.ProblemDetector;
import cloud.sync.problems.VirtualMachineProblems;
import com.google.inject.Inject;
import models.generic.RemoteState;
import models.service.VirtualMachineModelService;
import play.Logger;
import util.logging.Loggers;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 11.11.16.
 */
public class VirtualMachineNotInDatabaseDetector
        implements ProblemDetector<VirtualMachineInLocation> {

    private final VirtualMachineModelService virtualMachineModelService;
    private final static Logger.ALogger LOGGER = Loggers.of(Loggers.CLOUD_SYNC);

    @Inject
    public VirtualMachineNotInDatabaseDetector(
            VirtualMachineModelService virtualMachineModelService) {
        checkNotNull(virtualMachineModelService, "virtualMachineModelService is null.");
        this.virtualMachineModelService = virtualMachineModelService;
    }

    @Override
    public Optional<Problem<VirtualMachineInLocation>> apply(VirtualMachineInLocation virtualMachineInLocation) {

        //do not run this detector if a virtual machine is in progress as this cause problems
        //with directly reporting newly created virtual machines as problem
        if (virtualMachineModelService.getAll().stream().filter(
                virtualMachine -> RemoteState.INPROGRESS.equals(virtualMachine.getRemoteState()))
                .findAny().isPresent()) {
            LOGGER.debug(String
                    .format("Skipping execution of %s, as a virtual machine is currently in progress.",
                            this));
            return Optional.empty();
        }

        if (virtualMachineModelService.getByRemoteId(virtualMachineInLocation.id()) == null) {
            return Optional.of(new VirtualMachineProblems.VirtualMachineNotInDatabase(
                    virtualMachineInLocation));
        }

        return Optional.empty();
    }
}
