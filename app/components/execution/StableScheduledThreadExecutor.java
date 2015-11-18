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

import play.Logger;
import util.Loggers;

import java.util.concurrent.TimeUnit;

/**
 * Created by daniel on 27.08.15.
 */
public class StableScheduledThreadExecutor implements ExecutionService {

    private final static Logger.ALogger LOGGER = Loggers.of(Loggers.EXECUTION);

    private final ExecutionService executionService;

    public StableScheduledThreadExecutor(ExecutionService executionService) {
        this.executionService = executionService;
    }

    private Schedulable wrapSchedulableIfNeeded(final Schedulable schedulable) {

        Schedulable wrappedSchedulable = schedulable;
        if (schedulable.getClass().isAnnotationPresent(Stable.class)) {
            wrappedSchedulable = new StableSchedulable(schedulable);
        }
        return schedulable;
    }

    @Override public void schedule(Schedulable schedulable) {
        executionService.schedule(wrapSchedulableIfNeeded(schedulable));
    }

    @Override public void execute(Runnable runnable) {
        executionService.execute(runnable);
    }

    @Override public void shutdown() {
        executionService.shutdown();
    }

    private static class StableSchedulable implements Schedulable {

        private final Schedulable delegate;

        private StableSchedulable(Schedulable delegate) {
            this.delegate = delegate;
        }

        @Override public long period() {
            return delegate.period();
        }

        @Override public long delay() {
            return delegate.delay();
        }

        @Override public TimeUnit timeUnit() {
            return delegate.timeUnit();
        }

        @Override public void run() {
            try {
                LOGGER.info(
                    String.format("Running %s in stable context", delegate.getClass().getName()));
                delegate.run();
            } catch (Exception e) {
                LOGGER.error(String.format(
                    "Encountered error in stable schedulable %s, catched to allow further execution.",
                    delegate), e);
            }
        }
    }

}
