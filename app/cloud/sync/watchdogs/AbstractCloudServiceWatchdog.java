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

package cloud.sync.watchdogs;

import cloud.sync.Problem;
import cloud.sync.ProblemDetector;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.Set;

import cloud.CloudService;
import components.execution.SimpleBlockingQueue;



/**
 * Created by daniel on 27.04.15.
 */
public abstract class AbstractCloudServiceWatchdog<T> extends AbstractWatchDog<T> {

    private final CloudService cloudService;

    @Inject protected AbstractCloudServiceWatchdog(
        @Named(value = "problemQueue") SimpleBlockingQueue<Problem> problemQueue,
        Set<ProblemDetector<T>> problemDetectors, CloudService cloudService) {
        super(problemQueue, problemDetectors);
        this.cloudService = cloudService;
    }

    protected final CloudService cloudService() {
        return cloudService;
    }

}


