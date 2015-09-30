/*
 * Copyright (c) 2014-2015 University of Ulm
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

import cloud.resources.HardwareInLocation;
import cloud.sync.Problem;
import cloud.sync.Solution;
import cloud.sync.SolutionException;
import cloud.sync.problems.HardwareProblems;
import com.google.inject.Inject;
import models.CloudCredential;
import models.Hardware;
import models.service.HardwareModelService;
import models.service.ModelService;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 07.05.15.
 */
public class ConnectHardwareToCredential implements Solution {

    private final HardwareModelService hardwareModelService;
    private final ModelService<CloudCredential> cloudCredentialModelService;

    @Inject public ConnectHardwareToCredential(HardwareModelService hardwareModelService,
        ModelService<CloudCredential> cloudCredentialModelService) {
        this.hardwareModelService = hardwareModelService;
        this.cloudCredentialModelService = cloudCredentialModelService;
    }

    @Override public boolean isSolutionFor(Problem problem) {
        return problem instanceof HardwareProblems.HardwareMissesCredential;
    }

    @Override public void applyTo(Problem problem) throws SolutionException {
        checkArgument(isSolutionFor(problem));
        HardwareInLocation hardwareInLocation =
            ((HardwareProblems.HardwareMissesCredential) problem).getHardwareInLocation();

        Hardware modelHardware = hardwareModelService.getByRemoteId(hardwareInLocation.id());
        CloudCredential cloudCredential =
            cloudCredentialModelService.getByUuid(hardwareInLocation.credential());

        if (modelHardware == null || cloudCredential == null) {
            throw new SolutionException();
        }
        modelHardware.addCloudCredential(cloudCredential);
        hardwareModelService.save(modelHardware);
    }



}
