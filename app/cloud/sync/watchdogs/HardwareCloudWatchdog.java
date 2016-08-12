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

package cloud.sync.watchdogs;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import cloud.sword.CloudService;
import cloud.sword.resources.HardwareInLocation;
import cloud.sync.AbstractCloudServiceWatchdog;
import cloud.sync.Problem;
import cloud.sync.ProblemDetector;
import components.execution.SimpleBlockingQueue;
import components.execution.Stable;

/**
 * Created by daniel on 07.05.15.
 */
@Stable public class HardwareCloudWatchdog
    extends AbstractCloudServiceWatchdog<HardwareInLocation> {

    @Inject protected HardwareCloudWatchdog(
        @Named(value = "problemQueue") SimpleBlockingQueue<Problem> problemQueue,
        Set<ProblemDetector<HardwareInLocation>> problemDetectors, CloudService cloudService) {
        super(problemQueue, problemDetectors, cloudService);
    }

    @Override public String toString() {
        return "HardwareWatchdog";
    }

    @Override protected Iterable<HardwareInLocation> toWatch() {
        return cloudService().discoveryService().listHardwareFlavors();
    }

    @Override public long period() {
        return 20;
    }

    @Override public long delay() {
        return 10;
    }

    @Override public TimeUnit timeUnit() {
        return TimeUnit.SECONDS;
    }
}
