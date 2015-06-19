package cloud;

import cloud.resources.HardwareInLocation;
import cloud.resources.ImageInLocation;
import cloud.resources.LocationInCloud;
import cloud.resources.VirtualMachineInLocation;
import com.google.inject.Inject;
import de.uniulm.omi.cloudiator.sword.api.service.DiscoveryService;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

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
