package cloud;

import com.google.common.base.Optional;
import com.google.common.net.HostAndPort;
import de.uniulm.omi.cloudiator.sword.api.domain.*;
import de.uniulm.omi.cloudiator.sword.api.domain.VirtualMachineTemplate;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import de.uniulm.omi.cloudiator.sword.api.ssh.SshConnection;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by daniel on 17.04.15.
 */
public class SwordCloudService implements CloudService {

    private final ComputeService computeService;
    private final String cloud;
    private final String credential;

    public SwordCloudService(ComputeService computeService, String cloud, String credential) {
        this.computeService = computeService;
        this.cloud = cloud;
        this.credential = credential;
    }

    @Nullable @Override public ImageInCloudAndLocation getImage(String s) {
        final Image image = computeService.getImage(s);
        if (image == null) {
            return null;
        }
        return new ImageInCloudAndLocation(image, cloud, credential);
    }

    @Nullable @Override public VirtualMachineInCloudAndLocation getVirtualMachine(String s) {
        final VirtualMachine virtualMachine = computeService.getVirtualMachine(s);
        if (virtualMachine == null) {
            return null;
        }
        return new VirtualMachineInCloudAndLocation(virtualMachine, cloud, credential);
    }

    @Nullable @Override public LocationInCloud getLocation(String s) {
        final Location location = computeService.getLocation(s);
        if (location == null) {
            return null;
        }
        return new LocationInCloud(location, cloud, credential);
    }

    @Nullable @Override public HardwareInCloudAndLocation getHardware(String s) {
        final HardwareFlavor hardwareFlavor = computeService.getHardwareFlavor(s);
        if (hardwareFlavor == null) {
            return null;
        }
        return new HardwareInCloudAndLocation(hardwareFlavor, cloud, credential);
    }

    @Override public Iterable<HardwareInCloudAndLocation> listHardware() {
        Set<HardwareInCloudAndLocation> hardwareInCloudAndLocations = new HashSet<>();
        for (HardwareFlavor hardwareFlavor : computeService.listHardwareFlavors()) {
            if (hardwareFlavor != null) {
                hardwareInCloudAndLocations
                    .add(new HardwareInCloudAndLocation(hardwareFlavor, cloud, credential));
            }
        }
        return hardwareInCloudAndLocations;
    }

    @Override public Iterable<ImageInCloudAndLocation> listImages() {
        Set<ImageInCloudAndLocation> imageInCloudAndLocations = new HashSet<>();
        for (Image image : computeService.listImages()) {
            imageInCloudAndLocations.add(new ImageInCloudAndLocation(image, cloud, credential));
        }
        return imageInCloudAndLocations;
    }

    @Override public Iterable<LocationInCloud> listLocations() {
        Set<LocationInCloud> locationInClouds = new HashSet<>();
        for (Location location : computeService.listLocations()) {
            locationInClouds.add(new LocationInCloud(location, cloud, credential));
        }
        return locationInClouds;
    }

    @Override public Iterable<VirtualMachineInCloudAndLocation> listVirtualMachines() {
        Set<VirtualMachineInCloudAndLocation> virtualMachineInCloudAndLocations = new HashSet<>();
        for (VirtualMachine virtualMachine : computeService.listVirtualMachines()) {
            virtualMachineInCloudAndLocations
                .add(new VirtualMachineInCloudAndLocation(virtualMachine, cloud, credential));
        }
        return virtualMachineInCloudAndLocations;
    }

    @Override public void deleteVirtualMachine(String s) {
        computeService.deleteVirtualMachine(s);
    }

    @Override public VirtualMachineInCloudAndLocation createVirtualMachine(
        VirtualMachineTemplate virtualMachineTemplate) {
        return new VirtualMachineInCloudAndLocation(
            computeService.createVirtualMachine(virtualMachineTemplate), cloud, credential);
    }

    @Override public SshConnection getSshConnection(HostAndPort hostAndPort) {
        return computeService.getSshConnection(hostAndPort);
    }

    @Override public Optional<PublicIpService> getPublicIpService() {
        return computeService.getPublicIpService();
    }
}
