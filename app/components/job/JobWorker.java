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

package components.job;

import play.Logger;
import util.logging.Loggers;

/**
 * Created by daniel on 26.11.15.
 */
class JobWorker implements Runnable {

    private final static Logger.ALogger LOGGER = Loggers.of(Loggers.CLOUD_JOB);

    private final Job job;

    public JobWorker(Job job) {
        this.job = job;
    }

    @Override public void run() {

        LOGGER.info(String.format("Starting execution of job %s", job));
        try {
            job.execute();
            job.onSuccess();
            LOGGER.info(String.format("Execution of job %s successfully finished", job));
        } catch (Exception e) {
            LOGGER
                .error(String.format("Error during execution of %s, calling onError handler", job),
                    e);
            try {
                job.onError();
            } catch (JobException ignored) {
                LOGGER.error("Error in onError handler. Ignoring", ignored);
            }
        }



    }
}
