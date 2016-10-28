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

import de.uniulm.omi.cloudiator.lance.application.DeploymentContext;
import de.uniulm.omi.cloudiator.lance.application.component.*;
import models.ApplicationComponent;
import models.PortProvided;
import models.PortRequired;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 11.10.16.
 */
public class ApplicationComponentDeploymentContextVisitor {

    private final ApplicationComponent applicationComponent;

    public ApplicationComponentDeploymentContextVisitor(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }

    public DeploymentContext registerAtDeploymentContext(DeploymentContext deploymentContext) {
        // add all ingoing ports / provided ports
        for (PortProvided portProvided : applicationComponent.getProvidedPorts()) {
            deploymentContext
                .setProperty(portProvided.name(), portProvided.getPort(), InPort.class);
        }
        for (PortRequired portRequired : applicationComponent.getRequiredPorts()) {
            checkState(portRequired.communication() != null,
                String.format("portRequired %s is missing communication entity", portRequired));
            deploymentContext.setProperty(portRequired.name(), new PortReference(ComponentId
                .fromString(portRequired.communication().getProvidedPort().getApplicationComponent()
                    .getUuid()), portRequired.communication().getProvidedPort().name(),
                PortProperties.PortLinkage.ALL), OutPort.class);
        }

        return deploymentContext;
    }

}
