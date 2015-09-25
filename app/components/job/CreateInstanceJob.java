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
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import de.uniulm.omi.cloudiator.common.OneWayConverter;
import de.uniulm.omi.cloudiator.lance.application.ApplicationId;
import de.uniulm.omi.cloudiator.lance.application.ApplicationInstanceId;
import de.uniulm.omi.cloudiator.lance.application.DeploymentContext;
import de.uniulm.omi.cloudiator.lance.application.component.*;
import de.uniulm.omi.cloudiator.lance.client.DeploymentHelper;
import de.uniulm.omi.cloudiator.lance.client.LifecycleClient;
import de.uniulm.omi.cloudiator.lance.container.spec.os.OperatingSystem;
import de.uniulm.omi.cloudiator.lance.lca.LcaException;
import de.uniulm.omi.cloudiator.lance.lca.container.ContainerException;
import de.uniulm.omi.cloudiator.lance.lca.registry.RegistrationException;
import de.uniulm.omi.cloudiator.lance.lifecycle.LifecycleHandler;
import de.uniulm.omi.cloudiator.lance.lifecycle.LifecycleHandlerType;
import de.uniulm.omi.cloudiator.lance.lifecycle.LifecycleStore;
import de.uniulm.omi.cloudiator.lance.lifecycle.LifecycleStoreBuilder;
import de.uniulm.omi.cloudiator.lance.lifecycle.bash.BashBasedHandlerBuilder;
import models.*;
import models.generic.RemoteState;
import models.service.ModelService;

import javax.annotation.Nullable;
import java.util.Map;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 03.08.15.
 */
public class CreateInstanceJob extends GenericJob<Instance> {

    private final Instance instance;

    @Inject public CreateInstanceJob(Instance instance, ModelService<Instance> modelService,
        ModelService<Tenant> tenantModelService, ColosseumComputeService colosseumComputeService,
        Tenant tenant) {
        super(instance, modelService, tenantModelService, colosseumComputeService, tenant);
        this.instance = instance;
    }

    @Override protected void doWork(Instance instance, ModelService<Instance> modelService,
        ColosseumComputeService computeService, Tenant tenant) throws JobException {
        try {
            buildClient(instance);
        } catch (RegistrationException | LcaException | ContainerException e) {
            throw new JobException(e);
        }
    }

    private LifecycleClient buildClient(Instance instance)
        throws RegistrationException, LcaException, ContainerException {

        final LifecycleClient client = LifecycleClient.getClient();
        final ApplicationInstanceId applicationInstanceId =
            ApplicationInstanceId.fromString(instance.getApplicationInstance().getUuid());

        final ApplicationId applicationId =
            ApplicationId.fromString(instance.getApplicationInstance().getApplication().getUuid());

        //register application instance
        final boolean couldRegister =
            client.registerApplicationInstance(applicationInstanceId, applicationId);

        if (couldRegister) {
            registerApplicationComponents(instance, applicationInstanceId, client);
        }

        final DeploymentContext deploymentContext = buildDeploymentContext(instance,
            client.initDeploymentContext(applicationId, applicationInstanceId));

        checkState(instance.getVirtualMachine().publicIpAddress().isPresent());

        client
            .deploy(instance.getVirtualMachine().publicIpAddress().get().getIp(), deploymentContext,
                buildDeployableComponent(instance),
                instance.getVirtualMachine().operatingSystemVendorTypeOrDefault().lanceOs(),
                instance.getApplicationComponent().containerTypeOrDefault());

        return client;

    }

    private void registerApplicationComponents(Instance instance,
        ApplicationInstanceId applicationInstanceId, LifecycleClient client)
        throws RegistrationException {

        for (ApplicationComponent applicationComponent : instance.getApplicationInstance()
            .getApplication().getApplicationComponents()) {
            client.registerComponentForApplicationInstance(applicationInstanceId,
                ComponentId.fromString(applicationComponent.getUuid()),
                applicationComponent.getComponent().getName());
        }
    }

    private DeployableComponent buildDeployableComponent(Instance instance) {

        final DeployableComponentBuilder builder = DeployableComponentBuilder
            .createBuilder(instance.getApplicationComponent().getComponent().getName(),
                ComponentId.fromString(instance.getApplicationComponent().getUuid()));

        // add all ingoing ports / provided ports
        for (PortProvided portProvided : instance.getApplicationComponent().getProvidedPorts()) {
            PortProperties.PortType portType;
            if (portProvided.getAttachedCommunication() == null) {
                portType = PortProperties.PortType.PUBLIC_PORT;
            } else {
                // todo should be internal, but for the time being we use public here
                // facilitates the security group handling
                //portType = PortProperties.PortType.INTERNAL_PORT;
                portType = PortProperties.PortType.PUBLIC_PORT;
            }
            builder.addInport(portProvided.name(), portType, PortProperties.INFINITE_CARDINALITY);
        }
        //add all outgoing ports / required ports
        for (PortRequired portRequired : instance.getApplicationComponent().getRequiredPorts()) {
            //check if something better for null todo
            builder.addOutport(portRequired.name(), DeploymentHelper.getEmptyPortUpdateHandler(),
                PortProperties.INFINITE_CARDINALITY);
        }

        //build a lifecycle store from the application component
        builder.addLifecycleStore(new LifecycleComponentToLifecycleStoreConverter(
            instance.getVirtualMachine().operatingSystemVendorTypeOrDefault().lanceOs())
            .apply((LifecycleComponent) instance.getApplicationComponent().getComponent()));

        return builder.build();


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
                .fromString(portRequired.getAttachedCommunication().getProvidedPort()
                    .getApplicationComponent().getUuid()),
                portRequired.getAttachedCommunication().getProvidedPort().name(),
                PortProperties.PortLinkage.ALL), OutPort.class);
        }

        return deploymentContext;
    }

    @Override public boolean canStart() {
        return RemoteState.OK.equals(instance.getVirtualMachine().getRemoteState());
    }

    private static class LifecycleComponentToLifecycleStoreConverter
        implements OneWayConverter<LifecycleComponent, LifecycleStore> {

        private final OperatingSystem os;

        public LifecycleComponentToLifecycleStoreConverter(OperatingSystem os) {
            this.os = os;
        }

        private Map<LifecycleHandlerType, String> buildCommandMap(LifecycleComponent lc) {
            Map<LifecycleHandlerType, String> commands = Maps.newHashMap();
            if (lc.getInit() != null) {
                commands.put(LifecycleHandlerType.INIT, lc.getInit());
            }
            if (lc.getPreInstall() != null) {
                commands.put(LifecycleHandlerType.PRE_INSTALL, lc.getPreInstall());
            }
            if (lc.getInstall() != null) {
                commands.put(LifecycleHandlerType.INSTALL, lc.getInstall());
            }
            if (lc.getPostInstall() != null) {
                commands.put(LifecycleHandlerType.POST_INSTALL, lc.getPostInstall());
            }
            if (lc.getPreStart() != null) {
                commands.put(LifecycleHandlerType.PRE_START, lc.getPreStart());
            }
            commands.put(LifecycleHandlerType.START, lc.getStart());
            if (lc.getPostStart() != null) {
                commands.put(LifecycleHandlerType.POST_START, lc.getPostStart());
            }
            if (lc.getPreStop() != null) {
                commands.put(LifecycleHandlerType.PRE_STOP, lc.getPreStop());
            }
            if (lc.getStop() != null) {
                commands.put(LifecycleHandlerType.STOP, lc.getStop());
            }
            if (lc.getPostStop() != null) {
                commands.put(LifecycleHandlerType.POST_STOP, lc.getPostStop());
            }
            return commands;
        }

        @Nullable @Override public LifecycleStore apply(LifecycleComponent lc) {

            final LifecycleStoreBuilder lifecycleStoreBuilder = new LifecycleStoreBuilder();

            for (Map.Entry<LifecycleHandlerType, String> entry : buildCommandMap(lc).entrySet()) {
                final BashBasedHandlerBuilder bashBasedHandlerBuilder =
                    new BashBasedHandlerBuilder();
                bashBasedHandlerBuilder.setOperatingSystem(os);
                bashBasedHandlerBuilder.addCommand(entry.getValue());
                final LifecycleHandler lifecycleHandler =
                    bashBasedHandlerBuilder.build(entry.getKey());
                lifecycleStoreBuilder.setHandler(lifecycleHandler, entry.getKey());
            }

            return lifecycleStoreBuilder.build();
        }
    }



}
