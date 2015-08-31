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

package components.execution;

import com.google.inject.Singleton;
import play.Logger;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by daniel on 17.04.15.
 */
@Singleton class ScheduledThreadPoolExecutorExecutionService implements ExecutionService {

    private final ScheduledExecutorService scheduledExecutorService;

    private static final Logger.ALogger LOGGER = Logger.of("colosseum.execution");

    public ScheduledThreadPoolExecutorExecutionService(
        ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @Override public void schedule(Schedulable schedulable) {
        LOGGER.debug(String
            .format("Scheduling %s with initial delay of %s and period of %s %s", schedulable,
                schedulable.delay(), schedulable.period(), schedulable.timeUnit()));
        this.scheduledExecutorService
            .scheduleAtFixedRate(schedulable, schedulable.delay(), schedulable.period(),
                schedulable.timeUnit());
    }

    @Override public void execute(Runnable runnable) {
        LOGGER.debug("Executing " + runnable);
        this.scheduledExecutorService.execute(runnable);
    }
}
