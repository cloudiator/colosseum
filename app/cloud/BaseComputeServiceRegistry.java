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
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import de.uniulm.omi.cloudiator.sword.api.service.DiscoveryService;
import models.CloudCredential;
import models.service.ModelService;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 19.06.15.
 */
public class BaseComputeServiceRegistry implements ComputeServiceRegistry {

    private final ComputeServiceFactory computeServiceFactory;
    private final ModelService<CloudCredential> cloudCredentialModelService;

    BaseComputeServiceRegistry(ComputeServiceFactory computeServiceFactory,
        ModelService<CloudCredential> cloudCredentialModelService) {
        this.computeServiceFactory = computeServiceFactory;
        this.cloudCredentialModelService = cloudCredentialModelService;
    }

    @Override
    public Set<ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation>> getComputeServices() {
        return getComputeServices(cloudCredentialModelService.getAll());
    }

    @Override
    public Iterable<DiscoveryService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation>> getDiscoveryServices() {
        Set<DiscoveryService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation>>
            discoveryServices = Sets.newHashSet();
        for (ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> computeService : getComputeServices()) {
            discoveryServices.add(computeService.discoveryService());
        }
        return discoveryServices;
    }

    @Override
    public ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> getComputeService(
        String cloudCredentialUuid) {
        Set<ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation>>
            computeServices = getComputeServices(
            Collections.singletonList(cloudCredentialModelService.getByUuid(cloudCredentialUuid)));
        checkState(computeServices.size() == 1);
        return computeServices.iterator().next();
    }

    @Override
    public Set<ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation>> getComputeServices(
        List<CloudCredential> cloudCredentials) {
        return ImmutableSet.copyOf(
            cloudCredentials.stream().map(computeServiceFactory::computeService)
                .collect(Collectors.toList()));
    }

    @Override
    public Iterator<ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation>> iterator() {
        return ImmutableSet.copyOf(getComputeServices()).iterator();
    }
}
