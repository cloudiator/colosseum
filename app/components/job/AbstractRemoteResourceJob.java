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
import com.google.common.base.MoreObjects;
import models.Tenant;
import models.generic.RemoteResource;
import models.generic.RemoteState;
import models.service.ModelService;
import models.service.RemoteModelService;
import play.db.jpa.JPAApi;

/**
 * Created by daniel on 08.05.15.
 */
public abstract class AbstractRemoteResourceJob<T extends RemoteResource> implements Job {

    private final String resourceUuid;
    private final String tenantUuid;
    private final ModelService<T> modelService;
    private final ColosseumComputeService colosseumComputeService;
    private JobState jobState;
    private final ModelService<Tenant> tenantModelService;
    private final JPAApi jpaApi;

    public AbstractRemoteResourceJob(JPAApi jpaApi, T t, RemoteModelService<T> modelService,
        ModelService<Tenant> tenantModelService, ColosseumComputeService colosseumComputeService,
        Tenant tenant) {
        this.colosseumComputeService = colosseumComputeService;
        this.resourceUuid = t.getUuid();
        this.modelService = modelService;
        this.tenantModelService = tenantModelService;
        this.tenantUuid = tenant.getUuid();
        this.jpaApi = jpaApi;
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
        init();
        this.doWork(modelService, colosseumComputeService);
    }

    protected final JPAApi jpaApi() {
        return this.jpaApi;
    }

    protected final T getT() {
        /**
         * todo: find a better way for waiting for the database
         */
        T t = null;
        while (t == null) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            t = this.modelService.getByUuid(resourceUuid);
        }
        return t;
    }

    protected final Tenant getTenant() {
        return this.tenantModelService.getByUuid(tenantUuid);
    }

    public void init() {
        jpaApi().withTransaction(() -> {
            T t = getT();
            t.setRemoteState(RemoteState.INPROGRESS);
            modelService.save(t);
        });
    }

    protected abstract void doWork(ModelService<T> modelService,
        ColosseumComputeService computeService) throws JobException;

    @Override public void onSuccess() throws JobException {
        jpaApi().withTransaction(() -> {
            T t = getT();
            t.setRemoteState(RemoteState.OK);
            modelService.save(t);
        });
    }

    @Override public void onError() throws JobException {
        jpaApi().withTransaction(() -> {
            T t = getT();
            t.setRemoteState(RemoteState.ERROR);
            modelService.save(t);
        });
    }

    @Override public String toString() {
        return MoreObjects.toStringHelper(this).add("resource", resourceUuid)
            .add("tenant", tenantUuid).toString();
    }
}
