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
import de.uniulm.omi.cloudiator.lance.lca.container.ContainerType;
import models.Instance;
import models.Tenant;
import models.generic.RemoteState;
import models.service.ModelService;
import models.service.RemoteModelService;
import play.Configuration;
import play.db.jpa.JPAApi;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 29.02.16.
 */
public class DeleteInstanceJob extends AbstractRemoteResourceJob<Instance> {

    private final RemoteModelService<Instance> instanceRemoteModelService;
    private final Configuration configuration;

    @Inject
    public DeleteInstanceJob(Configuration configuration, JPAApi jpaApi, Instance instance,
                             RemoteModelService<Instance> modelService, ModelService<Tenant> tenantModelService,
                             ColosseumComputeService colosseumComputeService, Tenant tenant) {
        super(jpaApi, instance, modelService, tenantModelService, colosseumComputeService, tenant);
        this.instanceRemoteModelService = modelService;
        this.configuration = configuration;
    }

    @Override
    public boolean canStart() throws JobException {
        try {
            return jpaApi().withTransaction(() -> {
                Instance instance = getT();

                if (RemoteState.ERROR.equals(instance.getRemoteState())) {
                    throw new JobException(String
                            .format("Job %s can never start as instance %s is in error state.", this,
                                    instance));
                }

                return RemoteState.OK.equals(getT().getRemoteState());
            });
        } catch (Throwable throwable) {
            throw new JobException(throwable);
        }
    }

    @Override
    protected void doWork(ModelService<Instance> modelService,
                          ColosseumComputeService computeService) throws JobException {

        jpaApi().withTransaction(() -> {
            Instance instance = getT();
            checkState(instance.remoteId().isPresent(), "no remote id present on instance");
            checkState(instance.getVirtualMachine().publicIpAddress().isPresent(),
                    "unknown ip address of virtual machine");

            try {

                ContainerType containerType;
                if (!dockerInstalled()) {
                    containerType = ContainerType.PLAIN;
                } else {
                    containerType = instance.getApplicationComponent().containerType();
                }

                final boolean undeploy = LifecycleClient.getClient(instance.getVirtualMachine().publicIpAddress().get().getIp())
                        .undeploy(
                                ComponentInstanceId.fromString(instance.remoteId().get()), containerType);

                if (!undeploy) {
                    throw new JobException("undeploy did not work.");
                }


            } catch (DeploymentException e) {
                throw new JobException(e);
            }
        });
    }

    @Override
    public void onSuccess() throws JobException {
        jpaApi().withTransaction(() -> {
            Instance t = getT();
            instanceRemoteModelService.delete(t);
        });
    }

    private boolean dockerInstalled() {
        return this.configuration.getBoolean("colosseum.installer.linux.lance.docker.install.flag");
    }
}
