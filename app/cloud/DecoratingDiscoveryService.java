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

import de.uniulm.omi.cloudiator.sword.api.domain.HardwareFlavor;
import de.uniulm.omi.cloudiator.sword.api.domain.Image;
import de.uniulm.omi.cloudiator.sword.api.domain.Location;
import de.uniulm.omi.cloudiator.sword.api.domain.VirtualMachine;
import de.uniulm.omi.cloudiator.sword.api.service.DiscoveryService;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import cloud.resources.HardwareInLocation;
import cloud.resources.ImageInLocation;
import cloud.resources.LocationInCloud;
import cloud.resources.VirtualMachineInLocation;
import models.Cloud;
import models.CloudCredential;
import models.service.ModelService;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 30.09.15.
 */
public class DecoratingDiscoveryService implements
    DiscoveryService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> {

    private final DiscoveryService<HardwareFlavor, Image, Location, VirtualMachine> delegate;
    private final String cloudId;
    private final String cloudCredential;
    private final ModelService<Cloud> cloudModelService;
    private final ModelService<CloudCredential> cloudCredentialModelService;

    public DecoratingDiscoveryService(
        DiscoveryService<HardwareFlavor, Image, Location, VirtualMachine> delegate, String cloudId,
        String cloudCredential, ModelService<Cloud> cloudModelService,
        ModelService<CloudCredential> cloudCredentialModelService) {

        checkNotNull(delegate);
        checkNotNull(cloudId);
        checkArgument(!cloudId.isEmpty());
        checkNotNull(cloudCredential);
        checkArgument(!cloudCredential.isEmpty());
        checkNotNull(cloudModelService);
        checkNotNull(cloudCredentialModelService);

        this.delegate = delegate;
        this.cloudId = cloudId;
        this.cloudCredential = cloudCredential;
        this.cloudModelService = cloudModelService;
        this.cloudCredentialModelService = cloudCredentialModelService;
    }

    @Nullable @Override public ImageInLocation getImage(String s) {
        //TODO: implement this.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable @Override public VirtualMachineInLocation getVirtualMachine(String s) {
        //TODO: implement this.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable @Override public LocationInCloud getLocation(String s) {
        //TODO: implement this.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable @Override public HardwareInLocation getHardwareFlavor(String s) {
        //TODO: implement this.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override public Iterable<HardwareInLocation> listHardwareFlavors() {
        return StreamSupport.stream(delegate.listHardwareFlavors().spliterator(), false).map(
            hardwareFlavor -> new HardwareInLocation(hardwareFlavor, cloudId, cloudCredential,
                cloudModelService, cloudCredentialModelService)).collect(Collectors.toList());
    }

    @Override public Iterable<ImageInLocation> listImages() {
        return StreamSupport.stream(delegate.listImages().spliterator(), false).map(
            image -> new ImageInLocation(image, cloudId, cloudCredential, cloudModelService,
                cloudCredentialModelService)).collect(Collectors.toList());
    }

    @Override public Iterable<LocationInCloud> listLocations() {
        return StreamSupport.stream(delegate.listLocations().spliterator(), false).map(
            location -> new LocationInCloud(location, cloudId, cloudCredential, cloudModelService,
                cloudCredentialModelService)).collect(Collectors.toList());
    }

    @Override public Iterable<VirtualMachineInLocation> listVirtualMachines() {
        return StreamSupport.stream(delegate.listVirtualMachines().spliterator(), false).map(
            new DecoratingComputeService.VirtualMachineDecorator(cloudId, cloudCredential,
                cloudCredentialModelService, cloudModelService)).collect(Collectors.toList());
    }
}
