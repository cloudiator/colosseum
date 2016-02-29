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

package components.job;

import cloud.colosseum.ColosseumComputeService;
import com.google.inject.Inject;
import de.uniulm.omi.cloudiator.lance.client.LifecycleClient;
import de.uniulm.omi.cloudiator.lance.lca.DeploymentException;
import de.uniulm.omi.cloudiator.lance.lca.container.ComponentInstanceId;
import models.Instance;
import models.Tenant;
import models.service.ModelService;
import models.service.RemoteModelService;
import play.db.jpa.JPA;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 29.02.16.
 */
public class DeleteInstanceJob extends AbstractRemoteResourceJob<Instance> {

    @Inject public DeleteInstanceJob(Instance instance, RemoteModelService<Instance> modelService,
        ModelService<Tenant> tenantModelService, ColosseumComputeService colosseumComputeService,
        Tenant tenant) {
        super(instance, modelService, tenantModelService, colosseumComputeService, tenant);
    }

    @Override public boolean canStart() {
        return true;
    }

    @Override protected void doWork(ModelService<Instance> modelService,
        ColosseumComputeService computeService) throws JobException {

        JPA.withTransaction(() -> {
            Instance instance = getT();
            checkState(instance.remoteId().isPresent());
            checkState(instance.getVirtualMachine().publicIpAddress().isPresent());

            try {
                final boolean undeploy = LifecycleClient.getClient()
                    .undeploy(instance.getVirtualMachine().publicIpAddress().get().getIp(),
                        ComponentInstanceId.fromString(instance.remoteId().get()),
                        instance.getApplicationComponent().containerTypeOrDefault());

                if (!undeploy) {
                    throw new JobException("undeploy did not work.");
                }


            } catch (DeploymentException e) {
                throw new JobException(e);
            }
        });
    }

    @Override public void onSuccess() throws JobException {
        // do nothing
    }

    @Override public void onError() throws JobException {
        // do nothing
    }
}
