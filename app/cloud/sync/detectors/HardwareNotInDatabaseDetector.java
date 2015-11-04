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

package cloud.sync.detectors;

import cloud.resources.HardwareInLocation;
import cloud.sync.Problem;
import cloud.sync.ProblemDetector;
import cloud.sync.problems.HardwareProblems;
import com.google.inject.Inject;
import models.service.HardwareModelService;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 04.11.15.
 */
public class HardwareNotInDatabaseDetector implements ProblemDetector<HardwareInLocation> {

    private final HardwareModelService hardwareModelService;

    @Inject public HardwareNotInDatabaseDetector(HardwareModelService hardwareModelService) {
        checkNotNull(hardwareModelService);
        this.hardwareModelService = hardwareModelService;
    }

    @Override public Optional<Problem> apply(HardwareInLocation hardwareInLocation) {
        if (hardwareModelService.getByRemoteId(hardwareInLocation.id()) == null) {
            return Optional.of(new HardwareProblems.HardwareNotInDatabase(hardwareInLocation));
        }
        return Optional.empty();
    }
}
