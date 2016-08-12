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
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import cloud.sword.CloudService;
import cloud.colosseum.ColosseumComputeService;
import cloud.keypair.KeyPairStrategy;
import cloud.connection.RemoteConnectionStrategy;
import components.execution.SimpleBlockingQueue;
import components.model.ModelValidationService;
import models.Instance;
import models.Tenant;
import models.VirtualMachine;
import models.service.ModelService;
import models.service.PortProvidedService;
import models.service.RemoteModelService;
import play.Configuration;
import play.db.jpa.JPAApi;

/**
 * Created by daniel on 03.07.15.
 */
@Singleton public class BaseJobService implements JobService {

    private final RemoteModelService<VirtualMachine> virtualMachineModelService;
    private final ModelService<Tenant> tenantModelService;
    private final RemoteModelService<Instance> instanceModelService;
    private final ColosseumComputeService colosseumComputeService;
    private final SimpleBlockingQueue<Job> jobQueue;
    private final KeyPairStrategy keyPairStrategy;
    private final RemoteConnectionStrategy.RemoteConnectionStrategyFactory
        remoteConnectionStrategyFactory;
    private final PortProvidedService portProvidedService;
    private final ModelValidationService modelValidationService;
    private final Configuration configuration;
    private final JPAApi jpaApi;

    @Inject public BaseJobService(JPAApi jpaApi, Configuration configuration,
        RemoteModelService<VirtualMachine> virtualMachineModelService, CloudService cloudService,
        ModelService<Tenant> tenantModelService, RemoteModelService<Instance> instanceModelService,
        @Named("jobQueue") SimpleBlockingQueue<Job> jobQueue, KeyPairStrategy keyPairStrategy,
        RemoteConnectionStrategy.RemoteConnectionStrategyFactory remoteConnectionStrategyFactory,
        PortProvidedService portProvidedService, ModelValidationService modelValidationService) {
        this.virtualMachineModelService = virtualMachineModelService;
        this.tenantModelService = tenantModelService;
        this.instanceModelService = instanceModelService;
        this.keyPairStrategy = keyPairStrategy;
        this.remoteConnectionStrategyFactory = remoteConnectionStrategyFactory;
        this.portProvidedService = portProvidedService;
        this.modelValidationService = modelValidationService;
        this.colosseumComputeService = cloudService.computeService();
        this.jobQueue = jobQueue;
        this.configuration = configuration;
        this.jpaApi = jpaApi;
    }

    @Override public void newVirtualMachineJob(VirtualMachine virtualMachine, Tenant tenant) {
        this.jobQueue.add(
            new CreateVirtualMachineJob(jpaApi, virtualMachine, virtualMachineModelService,
                tenantModelService, colosseumComputeService, tenant, keyPairStrategy,
                remoteConnectionStrategyFactory, portProvidedService));
    }

    @Override public void newInstanceJob(Instance instance, Tenant tenant) {
        this.jobQueue.add(
            new CreateInstanceJob(configuration, jpaApi, instance, instanceModelService,
                tenantModelService, colosseumComputeService, tenant, modelValidationService));
    }

    @Override public void newDeleteVirtualMachineJob(VirtualMachine virtualMachine, Tenant tenant) {
        this.jobQueue.add(
            new DeleteVirtualMachineJob(jpaApi, virtualMachine, virtualMachineModelService,
                tenantModelService, colosseumComputeService, tenant));
    }

    @Override public void newDeleteInstanceJob(Instance instance, Tenant tenant) {
        this.jobQueue.add(
            new DeleteInstanceJob(configuration, jpaApi, instance, instanceModelService,
                tenantModelService, colosseumComputeService, tenant));
    }

}
