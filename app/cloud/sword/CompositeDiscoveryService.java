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

package cloud.sword;

import com.google.inject.Inject;

import de.uniulm.omi.cloudiator.sword.api.service.DiscoveryService;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

import cloud.sword.resources.HardwareInLocation;
import cloud.sword.resources.ImageInLocation;
import cloud.sword.resources.LocationInCloud;
import cloud.sword.resources.VirtualMachineInLocation;

/**
 * Created by daniel on 17.04.15.
 */
public class CompositeDiscoveryService implements
    DiscoveryService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> {

    private final Iterable<? extends DiscoveryService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation>>
        discoveryServices;

    @Inject public CompositeDiscoveryService(
        Iterable<? extends DiscoveryService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation>> discoveryServices) {
        this.discoveryServices = discoveryServices;
    }

    @Nullable @Override public ImageInLocation getImage(String s) {
        //TODO: implement
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable @Override public VirtualMachineInLocation getVirtualMachine(String s) {
        //TODO: implement
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable @Override public LocationInCloud getLocation(String s) {
        //TODO: implement
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable @Override public HardwareInLocation getHardwareFlavor(String s) {
        //TODO: implement
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override public Iterable<HardwareInLocation> listHardwareFlavors() {
        List<HardwareInLocation> hardwareInLocations = new LinkedList<>();
        for (DiscoveryService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> discoveryService : discoveryServices) {
            for (HardwareInLocation hardwareInLocation : discoveryService.listHardwareFlavors()) {
                hardwareInLocations.add(hardwareInLocation);
            }
        }
        return hardwareInLocations;
    }

    @Override public Iterable<ImageInLocation> listImages() {
        List<ImageInLocation> images = new LinkedList<>();
        for (DiscoveryService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> discoveryService : discoveryServices) {
            for (ImageInLocation image : discoveryService.listImages()) {
                images.add(image);
            }
        }
        return images;
    }

    @Override public Iterable<LocationInCloud> listLocations() {
        List<LocationInCloud> locations = new LinkedList<>();
        for (DiscoveryService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> discoveryService : discoveryServices) {
            for (LocationInCloud location : discoveryService.listLocations()) {
                locations.add(location);
            }
        }
        return locations;
    }

    @Override public Iterable<VirtualMachineInLocation> listVirtualMachines() {
        List<VirtualMachineInLocation> virtualMachines = new LinkedList<>();
        for (DiscoveryService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> discoveryService : discoveryServices) {
            for (VirtualMachineInLocation virtualMachine : discoveryService.listVirtualMachines()) {
                virtualMachines.add(virtualMachine);
            }
        }
        return virtualMachines;
    }
}
