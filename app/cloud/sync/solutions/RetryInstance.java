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

package cloud.sync.solutions;

import cloud.sync.Problem;
import cloud.sync.Solution;
import cloud.sync.SolutionException;
import cloud.sync.problems.InstanceProblems;
import com.google.inject.Inject;
import components.job.JobService;
import models.Instance;
import models.generic.RemoteState;
import models.service.ModelService;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 14.11.16.
 */
public class RetryInstance implements Solution {

    private final JobService jobService;
    private final ModelService<Instance> instanceModelService;

    @Inject
    public RetryInstance(JobService jobService, ModelService<Instance> instanceModelService) {

        checkNotNull(instanceModelService, "instanceModelService is null.");

        this.instanceModelService = instanceModelService;

        checkNotNull(jobService, "jobService is null.");

        this.jobService = jobService;
    }

    @Override public boolean isSolutionFor(Problem problem) {
        return problem instanceof InstanceProblems.InstanceInErrorState;
    }

    @Override public void applyTo(Problem problem) throws SolutionException {
        checkState(isSolutionFor(problem));

        InstanceProblems.InstanceInErrorState instanceInErrorState =
            (InstanceProblems.InstanceInErrorState) problem;

        Instance instance =
            instanceModelService.getById(instanceInErrorState.getResource().getId());

        if (instance == null) {
            throw new SolutionException("Instance does not longer exist.");
        }

        if (!RemoteState.ERROR.equals(instance.getRemoteState()) || !instance.remoteId().isPresent()) {
            return;
        }

        checkState(instance.tenant().isPresent(), "Tenant of instance is not present.");

        instance.unbind();
        instanceModelService.save(instance);

        jobService.newInstanceJob(instance, instance.tenant().get());
    }
}
