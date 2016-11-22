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

package cloud;

import cloud.resources.HardwareInLocation;
import cloud.resources.ImageInLocation;
import cloud.resources.LocationInCloud;
import cloud.resources.VirtualMachineInLocation;
import com.google.inject.Inject;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import de.uniulm.omi.cloudiator.sword.core.properties.PropertiesBuilder;
import de.uniulm.omi.cloudiator.sword.service.ServiceBuilder;
import models.Cloud;
import models.CloudCredential;
import models.service.ModelService;
import play.Configuration;
import util.ConfigurationConstants;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;


/**
 * Created by daniel on 19.06.15.
 */
public class SwordComputeServiceFactory implements ComputeServiceFactory {

    private final ModelService<Cloud> cloudModelService;
    private final ModelService<CloudCredential> cloudCredentialModelService;
    private final Configuration configuration;


    @Inject SwordComputeServiceFactory(ModelService<Cloud> cloudModelService,
        ModelService<CloudCredential> cloudCredentialModelService, Configuration configuration) {

        checkNotNull(cloudModelService, "cloudModelService is null.");
        checkNotNull(cloudCredentialModelService, "cloudCredentialModelService is null.");
        checkNotNull(configuration, "configuration is null.");

        this.cloudModelService = cloudModelService;
        this.cloudCredentialModelService = cloudCredentialModelService;
        this.configuration = configuration;
    }

    private String getNodeGroup() {
        final String nodeGroup =
            configuration.getString(ConfigurationConstants.NODE_GROUP);
        checkState(nodeGroup != null, String
            .format("No nodeGroup configured! Make sure property %s is set.",
                ConfigurationConstants.NODE_GROUP));
        return nodeGroup;
    }

    @Override
    public ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> computeService(
        CloudCredential cloudCredential) {

        checkNotNull(cloudCredential);

        return new DecoratingComputeService(ServiceBuilder
            .newServiceBuilder(cloudCredential.getCloud().api().getInternalProviderName())
            .endpoint(cloudCredential.getCloud().getEndpoint().orElse(null))
            .credentials(cloudCredential.getUser(), cloudCredential.getSecret()).properties(
                PropertiesBuilder.newBuilder().putProperties(
                    new CompositeCloudPropertyProvider(cloudCredential.getCloud()).properties())
                    .build()).loggingModule(new SwordLoggingModule()).nodeGroup(getNodeGroup())
            .build(), cloudCredential.getCloud().getUuid(), cloudCredential.getUuid(),
            cloudModelService, cloudCredentialModelService);
    }

}
