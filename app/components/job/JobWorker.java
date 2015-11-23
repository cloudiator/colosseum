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

import com.google.inject.Inject;
import com.google.inject.name.Named;
import components.execution.Loop;
import components.execution.SimpleBlockingQueue;
import play.Logger;
import play.db.jpa.Transactional;


/**
 * Created by daniel on 12.05.15.
 */

public class JobWorker implements Runnable {

    private final SimpleBlockingQueue<Job> jobQueue;

    @Inject public JobWorker(@Named("jobQueue") SimpleBlockingQueue<Job> jobQueue) {
        this.jobQueue = jobQueue;
    }

    @Loop @Transactional @Override public void run() {

        Job job = null;
        try {
            job = jobQueue.take();
        } catch (InterruptedException e) {
            Logger.error("Job Execution got interrupted", e);
            Thread.currentThread().interrupt();
        }

        if (job != null) {
            Logger.info(String.format("Starting execution of job %s", job));
            try {
                job.execute();
                job.onSuccess();
                Logger.info(String.format("Execution of job %s successfully finished", job));
            } catch (Exception e) {
                Logger.error(
                    String.format("Error during execution of %s, calling onError handler", job), e);
                job.onError();
            }
        }
    }
}
