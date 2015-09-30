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
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import de.uniulm.omi.cloudiator.sword.api.domain.HardwareFlavor;
import de.uniulm.omi.cloudiator.sword.api.domain.Image;
import de.uniulm.omi.cloudiator.sword.api.domain.Location;
import de.uniulm.omi.cloudiator.sword.api.domain.VirtualMachine;
import de.uniulm.omi.cloudiator.sword.api.service.DiscoveryService;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 30.09.15.
 */
public class DecoratingDiscoveryService implements
    DiscoveryService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> {

    private final DiscoveryService<HardwareFlavor, Image, Location, VirtualMachine> delegate;
    private final String cloudId;
    private final String cloudCredential;

    public DecoratingDiscoveryService(
        DiscoveryService<HardwareFlavor, Image, Location, VirtualMachine> delegate, String cloudId,
        String cloudCredential) {
        this.delegate = delegate;
        this.cloudId = cloudId;
        this.cloudCredential = cloudCredential;
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
        return Iterables.transform(delegate.listHardwareFlavors(),
            new Function<HardwareFlavor, HardwareInLocation>() {
                @Nullable @Override
                public HardwareInLocation apply(@Nullable HardwareFlavor hardwareFlavor) {
                    checkNotNull(hardwareFlavor);
                    return new HardwareInLocation(hardwareFlavor, cloudId, cloudCredential);
                }
            });
    }

    @Override public Iterable<ImageInLocation> listImages() {
        return Iterables.transform(delegate.listImages(), new Function<Image, ImageInLocation>() {
            @Nullable @Override public ImageInLocation apply(@Nullable Image image) {
                checkNotNull(image);
                return new ImageInLocation(image, cloudId, cloudCredential);
            }
        });
    }

    @Override public Iterable<LocationInCloud> listLocations() {
        return Iterables
            .transform(delegate.listLocations(), new Function<Location, LocationInCloud>() {
                @Nullable @Override public LocationInCloud apply(@Nullable Location location) {
                    checkNotNull(location);
                    return new LocationInCloud(location, cloudId, cloudCredential);
                }
            });
    }

    @Override public Iterable<VirtualMachineInLocation> listVirtualMachines() {
        return Iterables.transform(delegate.listVirtualMachines(),
            new DecoratingComputeService.VirtualMachineDecorator(cloudId, cloudCredential));
    }
}
