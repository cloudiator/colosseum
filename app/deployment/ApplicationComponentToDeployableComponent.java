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

package deployment;

import de.uniulm.omi.cloudiator.lance.application.component.DeployableComponent;
import de.uniulm.omi.cloudiator.lance.application.component.DeployableComponentBuilder;
import de.uniulm.omi.cloudiator.lance.application.component.OutPort;
import de.uniulm.omi.cloudiator.lance.application.component.PortProperties;
import de.uniulm.omi.cloudiator.lance.client.DeploymentHelper;
import de.uniulm.omi.cloudiator.lance.lifecycle.bash.BashBasedHandlerBuilder;
import de.uniulm.omi.cloudiator.lance.lifecycle.detector.PortUpdateHandler;
import models.ApplicationComponent;
import models.LifecycleComponent;
import models.PortProvided;
import models.PortRequired;

import java.util.function.Function;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 11.10.16.
 */
public class ApplicationComponentToDeployableComponent
    implements Function<ApplicationComponent, DeployableComponent> {

    private final ApplicationComponentToComponentId applicationComponentToComponentId;
    private final LifecycleComponentToLifecycleStore lifecycleComponentToLifecycleStore;
    private final OsConverter osConverter;

    public ApplicationComponentToDeployableComponent() {
        applicationComponentToComponentId = new ApplicationComponentToComponentId();
        lifecycleComponentToLifecycleStore = new LifecycleComponentToLifecycleStore();
        osConverter = new OsConverter();
    }

    @Override public DeployableComponent apply(ApplicationComponent applicationComponent) {

        checkArgument(applicationComponent.getComponent() instanceof LifecycleComponent,
            this + "only supports " + LifecycleComponent.class);

        final DeployableComponentBuilder builder = DeployableComponentBuilder
            .createBuilder(applicationComponent.getComponent().getName(),
                applicationComponentToComponentId.apply(applicationComponent));

        // add all ingoing ports / provided ports
        for (PortProvided portProvided : applicationComponent.getProvidedPorts()) {
            PortProperties.PortType portType;
            if (portProvided.getAttachedCommunications().isEmpty()) {
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
        for (PortRequired portRequired : applicationComponent.getRequiredPorts()) {
            //check if something better for null todo
            final PortUpdateHandler portUpdateHandler;
            if (!portRequired.updateAction().isPresent()) {
                portUpdateHandler = DeploymentHelper.getEmptyPortUpdateHandler();
            } else {
                BashBasedHandlerBuilder portUpdateBuilder = new BashBasedHandlerBuilder();
                portUpdateBuilder.setOperatingSystem(osConverter.apply(
                    applicationComponent.getVirtualMachineTemplate().image().operatingSystem()));
                portUpdateBuilder.addCommand(portRequired.updateAction().get());
                portUpdateHandler = portUpdateBuilder.buildPortUpdateHandler();
            }

            int minSinks;
            if (portRequired.isMandatory()) {
                minSinks = 1;
            } else {
                minSinks = OutPort.NO_SINKS;
            }

            //todo this is inconsistent. multiple PortUpdateHandler should be allowed here so
            //todo it is possible to set one per operating system!
            builder.addOutport(portRequired.name(), portUpdateHandler,
                PortProperties.INFINITE_CARDINALITY, minSinks);
        }

        //build a lifecycle store from the application component
        builder.addLifecycleStore(lifecycleComponentToLifecycleStore
            .apply((LifecycleComponent) applicationComponent.getComponent()));

        return builder.build();
    }
}
