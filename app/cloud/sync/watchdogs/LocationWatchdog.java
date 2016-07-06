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

import cloud.CloudService;
import cloud.resources.LocationInCloud;
import cloud.sync.AbstractCloudServiceWatchdog;
import cloud.sync.Problem;
import cloud.sync.ProblemDetector;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import components.execution.SimpleBlockingQueue;
import components.execution.Stable;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by daniel on 05.05.15.
 */
@Stable public class LocationWatchdog extends AbstractCloudServiceWatchdog<LocationInCloud> {

    @Inject protected LocationWatchdog(
        @Named(value = "problemQueue") SimpleBlockingQueue<Problem> problemQueue,
        Set<ProblemDetector<LocationInCloud>> problemDetectors, CloudService cloudService) {
        super(problemQueue, problemDetectors, cloudService);
    }
    
    @Override public String toString() {
        return "LocationWatchdog";
    }

    @Override protected Iterable<LocationInCloud> toWatch() {
        return cloudService().discoveryService().listLocations();
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
