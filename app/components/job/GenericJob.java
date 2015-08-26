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

import cloud.colosseum.ColosseumComputeService;
import models.Tenant;
import models.generic.Model;
import models.service.ModelService;

/**
 * Created by daniel on 08.05.15.
 */
public abstract class GenericJob<T extends Model> implements Job {

    private final String resourceUuid;
    private final ModelService<T> modelService;
    private final ColosseumComputeService colosseumComputeService;
    private JobState jobState;
    private final Tenant tenant;
    private final ModelService<Tenant> tenantModelService;

    public GenericJob(T t, ModelService<T> modelService, ModelService<Tenant> tenantModelService,
        ColosseumComputeService colosseumComputeService, Tenant tenant) {
        this.colosseumComputeService = colosseumComputeService;
        this.resourceUuid = t.getUuid();
        this.modelService = modelService;
        this.tenantModelService = tenantModelService;
        this.tenant = tenant;
    }

    @Override public final String getResourceUuid() {
        return resourceUuid;
    }

    @Override public final JobState state() {
        return jobState;
    }

    @Override public final void state(JobState jobState) {
        this.jobState = jobState;
    }

    @Override public final int getPriority() {
        return Priority.HIGH;
    }

    @Override public final void execute() throws JobException {
        T t = null;
        /**

         * todo: find a better way for waiting for the database
         */
        while (t == null) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println(resourceUuid);
            t = this.modelService.getByUuid(resourceUuid);
        }
        this.doWork(t, modelService, colosseumComputeService,
            tenantModelService.getById(tenant.getId()));
        this.modelService.save(t);
    }

    protected abstract void doWork(T t, ModelService<T> modelService,
        ColosseumComputeService computeService, Tenant tenant) throws JobException;
}
