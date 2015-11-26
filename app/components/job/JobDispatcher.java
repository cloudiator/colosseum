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
import components.execution.ExecutionService;
import components.execution.Loop;
import components.execution.SimpleBlockingQueue;
import play.Logger;


/**
 * Created by daniel on 12.05.15.
 */

public class JobDispatcher implements Runnable {

    private final SimpleBlockingQueue<Job> jobQueue;
    private final ExecutionService executionService;

    @Inject public JobDispatcher(@Named("jobQueue") SimpleBlockingQueue<Job> jobQueue,
        ExecutionService executionService) {
        this.jobQueue = jobQueue;
        this.executionService = executionService;
    }

    @Loop @Override public void run() {

        Job job = null;
        try {
            job = jobQueue.take();
        } catch (InterruptedException e) {
            Logger.error("Job Execution got interrupted", e);
            Thread.currentThread().interrupt();
        }

        if (job != null) {
            try {
                if (job.canStart()) {
                    this.executionService.execute(new JobWorker(job));
                } else {
                    try {
                        //todo find better way for waiting here
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Logger.error("Job Execution got interrupted", e);
                        Thread.currentThread().interrupt();
                    }
                    jobQueue.add(job);
                }
            } catch (JobException e) {
                Logger.error("Can not start execution of job", e);
            }
        }
    }
}
