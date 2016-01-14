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

import cloud.colosseum.BaseColosseumComputeService;
import cloud.colosseum.ColosseumComputeService;
import cloud.resources.HardwareInLocation;
import cloud.resources.ImageInLocation;
import cloud.resources.LocationInCloud;
import cloud.resources.VirtualMachineInLocation;
import com.google.inject.Inject;
import de.uniulm.omi.cloudiator.sword.api.service.DiscoveryService;
import models.CloudCredential;
import models.service.ModelService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 20.05.15.
 */
public class DefaultCloudService implements CloudService {

    private final ModelService<CloudCredential> cloudCredentialModelService;
    private final ComputeServiceFactory computeServiceFactory;

    @Inject public DefaultCloudService(ModelService<CloudCredential> cloudCredentialModelService,
        ComputeServiceFactory computeServiceFactory) {

        checkNotNull(cloudCredentialModelService);
        checkNotNull(computeServiceFactory);


        this.cloudCredentialModelService = cloudCredentialModelService;
        this.computeServiceFactory = computeServiceFactory;

    }

    @Override
    public DiscoveryService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> discoveryService() {
        return new CompositeDiscoveryService(
            new BaseComputeServiceRegistry(computeServiceFactory, cloudCredentialModelService)
                .getDiscoveryServices());
    }

    @Override public ColosseumComputeService computeService() {
        return new BaseColosseumComputeService(
            new BaseComputeServiceRegistry(computeServiceFactory, cloudCredentialModelService));
    }
}
