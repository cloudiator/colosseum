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

import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.Logger;
import util.logging.Loggers;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 24.07.15.
 */
@Singleton public class ExecutionSystemInitialization {

    private final ExecutionService executionService;
    private static Logger.ALogger LOGGER = Loggers.of(Loggers.EXECUTION);
    private final Set<Runnable> runnables;
    private final Set<Schedulable> schedulables;

    @Inject
    public ExecutionSystemInitialization(ExecutionService executionService, Set<Runnable> runnables,
        Set<Schedulable> schedulables) {

        checkNotNull(executionService);
        checkNotNull(runnables);
        checkNotNull(schedulables);

        this.executionService = executionService;
        this.runnables = runnables;
        this.schedulables = schedulables;
    }

    public void init() {
        LOGGER.info("Initializing execution system.");

        LOGGER.debug(String.format("Using %s as execution service", executionService));

        LOGGER.debug(String.format("Running %s tasks.", runnables.size() + schedulables.size()));

        for (Runnable runnable : runnables) {
            LOGGER.trace(String.format("Starting initializing of runnable %s", runnable));
            executionService.execute(runnable);
        }
        for (Schedulable schedulable : schedulables) {
            LOGGER.trace(String.format("Starting initialization of schedulable %s", schedulable));
            executionService.schedule(schedulable);
        }
    }

    public void shutdown() {
        LOGGER.info("Shutting down execution service.");
        executionService.shutdown();
    }


}
