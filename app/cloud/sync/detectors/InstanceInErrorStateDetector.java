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

import cloud.sync.Problem;
import cloud.sync.ProblemDetector;
import cloud.sync.problems.InstanceProblems;
import models.Instance;
import models.generic.RemoteState;

import java.util.Optional;

/**
 * Created by daniel on 12.11.16.
 */
public class InstanceInErrorStateDetector implements ProblemDetector<Instance> {

    @Override public Optional<Problem<Instance>> apply(Instance instance) {

        if (RemoteState.ERROR.equals(instance.getRemoteState())) {
            return Optional.of(new InstanceProblems.InstanceInErrorState(instance));
        }

        return Optional.empty();
    }
}
