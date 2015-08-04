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
import com.google.inject.Inject;
import eu.dslab.client.LifecycleClient;
import eu.dslab.control.application.ApplicationId;
import eu.dslab.control.application.ApplicationInstanceId;
import eu.dslab.control.application.DeploymentContext;
import eu.dslab.control.application.component.*;
import eu.dslab.control.container.spec.os.OperatingSystem;
import eu.dslab.control.lifecycle.LifecycleHandler;
import eu.dslab.control.lifecycle.LifecycleHandlerType;
import eu.dslab.control.lifecycle.LifecycleStore;
import eu.dslab.control.lifecycle.LifecycleStoreBuilder;
import eu.dslab.control.lifecycle.bash.BashBasedHandlerBuilder;
import models.*;
import models.service.ModelService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 03.08.15.
 */
public class CreateInstanceJob extends GenericJob<Instance> {


    @Inject public CreateInstanceJob(Instance instance, ModelService<Instance> modelService,
        ModelService<Tenant> tenantModelService, ColosseumComputeService colosseumComputeService,
        Tenant tenant) {
        super(instance, modelService, tenantModelService, colosseumComputeService, tenant);
    }

    @Override protected void doWork(Instance instance, ModelService<Instance> modelService,
        ColosseumComputeService computeService, Tenant tenant) throws JobException {

        try {
            buildClient(instance);
        } catch (RemoteException | NotBoundException e) {
            throw new JobException(e);

        }

    }

    private LifecycleClient buildClient(Instance instance)
        throws RemoteException, NotBoundException {

        final LifecycleClient client = LifecycleClient.getClient();
        final ApplicationInstanceId applicationInstanceId =
            ApplicationInstanceId.fromString(instance.getApplicationInstance().getUuid());

        final ApplicationId applicationId =
            ApplicationId.fromString(instance.getApplicationInstance().getApplication().getUuid());

        //register application instance
        client.registerApplicationInstance(applicationInstanceId, applicationId);


        //register all components (do only if first application instance) todo
        for (ApplicationComponent applicationComponent : instance.getApplicationInstance()
            .getApplication().getApplicationComponents()) {
            client.registerComponentForApplicationInstance(applicationInstanceId,
                //add optional name argument todo
                ComponentId.fromString(applicationComponent.getUuid()));
        }

        final DeploymentContext deploymentContext = buildDeploymentContext(instance,
            client.initDeploymentContext(applicationId, applicationInstanceId));

        checkState(instance.getVirtualMachine().publicIpAddress() != null);

        client.deploy(instance.getVirtualMachine().publicIpAddress().getIp(), deploymentContext,
            buildDeployableComponent(instance), OperatingSystem.UBUNTU_14_04);

        return client;

    }

    private DeployableComponent buildDeployableComponent(Instance instance) {

        final DeployableComponentBuilder builder = DeployableComponentBuilder
            .createBuilder(instance.getApplicationComponent().getComponent().getName(),
                ComponentId.fromString(instance.getApplicationComponent().getUuid()));

        // add all ingoing ports / provided ports
        for (PortProvided portProvided : instance.getApplicationComponent().getProvidedPorts()) {
            builder.addInport(portProvided.name(), PortProperties.PortType.PUBLIC_PORT,
                PortProperties.INFINITE_CARDINALITY);
        }
        //add all outgoing ports / required ports
        for (PortRequired portRequired : instance.getApplicationComponent().getRequiredPorts()) {
            //check if something better for null todo
            builder.addOutport(portRequired.name(), null, PortProperties.INFINITE_CARDINALITY);
        }

        builder.addLifecycleStore(buildLifecycleStore(instance));

        return builder.build();


    }

    private LifecycleStore buildLifecycleStore(Instance instance) {

        final LifecycleStoreBuilder lifecycleStoreBuilder = new LifecycleStoreBuilder();
        LifecycleComponent lc =
            (LifecycleComponent) instance.getApplicationComponent().getComponent();
        final BashBasedHandlerBuilder bashBasedHandlerBuilder = new BashBasedHandlerBuilder();
        bashBasedHandlerBuilder.addCommand(lc.getStart());
        final LifecycleHandler lifecycleHandler =
            bashBasedHandlerBuilder.build(LifecycleHandlerType.START);
        lifecycleStoreBuilder.setHandler(lifecycleHandler, LifecycleHandlerType.START);
        return lifecycleStoreBuilder.build();
    }

    private DeploymentContext buildDeploymentContext(Instance instance,
        DeploymentContext deploymentContext) {

        // add all ingoing ports / provided ports
        for (PortProvided portProvided : instance.getApplicationComponent().getProvidedPorts()) {
            deploymentContext
                .setProperty(portProvided.name(), portProvided.getPort(), InPort.class);
        }
        for (PortRequired portRequired : instance.getApplicationComponent().getRequiredPorts()) {
            deploymentContext.setProperty(portRequired.name(), new PortReference(ComponentId
                .fromString(
                    portRequired.getCommunication().getProvidedPort().getApplicationComponent()
                        .getUuid()), portRequired.getCommunication().getProvidedPort().name(),
                PortProperties.PortLinkage.ALL), OutPort.class);
        }

        return deploymentContext;
    }

}
