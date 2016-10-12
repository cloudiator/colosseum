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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**
 * An extension of the ScheduledThreadPoolExecutor, that logs any errors occurring during
 * the execution of tasks, instead of silently ignoring them.
 */
public class LoggingScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {
    public LoggingScheduledThreadPoolExecutor(int corePoolSize) {
        super(corePoolSize);
    }

    public LoggingScheduledThreadPoolExecutor(int corePoolSize, ThreadFactory threadFactory) {
        super(corePoolSize, threadFactory);
    }

    @Override protected void afterExecute(final Runnable r, final Throwable t) {
        super.afterExecute(r, t);
        Throwable tToLog = t;
        if (tToLog == null && r instanceof Future<?>) {
            try {
                if (((Future) r).isDone() && !((Future) r).isCancelled()) {
                    ((Future) r).get();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                tToLog = e.getCause();
            }
        }
        if (tToLog != null) {
            Logger.error("Uncaught exception occurred during the execution of task.", tToLog);
        }
    }
}
