package cloud;

import cloud.util.CloudScopedId;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.net.HostAndPort;
import com.google.inject.Inject;
import de.uniulm.omi.cloudiator.sword.api.domain.*;
import de.uniulm.omi.cloudiator.sword.api.extensions.KeyPairService;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import de.uniulm.omi.cloudiator.sword.api.ssh.SshConnection;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by daniel on 17.04.15.
 */
public class CompositeComputeService implements ComputeService {

    private final Iterable<ComputeService> computeServices;

    @Inject public CompositeComputeService(Iterable<ComputeService> computeServices) {
        this.computeServices = computeServices;
    }

    @Nullable @Override public Image getImage(String s) {
        return null;
    }

    @Nullable @Override public VirtualMachine getVirtualMachine(String s) {
        return null;
    }

    @Nullable @Override public Location getLocation(String s) {
        return null;
    }

    @Nullable @Override public HardwareFlavor getHardwareFlavor(String s) {
        return null;
    }

    @Override public Iterable<HardwareFlavor> listHardwareFlavors() {
        List<HardwareFlavor> hardwareFlavors = new LinkedList<>();
        for (ComputeService computeService : computeServices) {
            for (HardwareFlavor hardwareFlavor : computeService.listHardwareFlavors()) {
                hardwareFlavors.add(hardwareFlavor);
            }
        }
        return hardwareFlavors;
    }

    @Override public Iterable<Image> listImages() {
        List<Image> images = new LinkedList<>();
        for (ComputeService computeService : computeServices) {
            for (Image image : computeService.listImages()) {
                images.add(image);
            }
        }
        return images;
    }

    @Override public Iterable<Location> listLocations() {
        List<Location> locations = new LinkedList<>();
        for (ComputeService computeService : computeServices) {
            for (Location location : computeService.listLocations()) {
                locations.add(location);
            }
        }
        return locations;
    }

    @Override public Iterable<VirtualMachine> listVirtualMachines() {
        return null;
    }

    @Override public void deleteVirtualMachine(String s) {

    }

    @Override
    public VirtualMachine createVirtualMachine(VirtualMachineTemplate virtualMachineTemplate) {
        if (virtualMachineTemplate instanceof ColosseumVirtualMachineTemplate) {
            ColosseumVirtualMachineTemplate colosseumVirtualMachineTemplate =
                (ColosseumVirtualMachineTemplate) virtualMachineTemplate;
            for (ComputeService computeService : computeServices) {
                if (computeService instanceof CloudAndCredentialAwareComputeService) {
                    CloudAndCredentialAwareComputeService cloudAndCredentialAwareComputeService =
                        (CloudAndCredentialAwareComputeService) computeService;

                    if (cloudAndCredentialAwareComputeService.cloud
                        .equals(colosseumVirtualMachineTemplate.getCloudUuid())
                        && cloudAndCredentialAwareComputeService.credential
                        .equals(colosseumVirtualMachineTemplate.getCloudCredentialUuid())) {
                        return cloudAndCredentialAwareComputeService
                            .createVirtualMachine(colosseumVirtualMachineTemplate);
                    }
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
        throw new IllegalStateException();
    }

    @Override public SshConnection getSshConnection(HostAndPort hostAndPort) {
        return null;
    }

    @Override public Optional<PublicIpService> getPublicIpService() {
        return null;
    }

    @Override public Optional<KeyPairService> getKeyPairService() {
        return null;
    }



    private static class CloudAndCredentialAwareComputeService implements ComputeService {

        private final String cloud;
        private final String credential;
        private final SwordComputeService swordComputeService;

        private CloudAndCredentialAwareComputeService(String cloud, String credential,
            SwordComputeService swordComputeService) {
            this.cloud = cloud;
            this.credential = credential;
            this.swordComputeService = swordComputeService;
        }

        @Nullable @Override public ImageInCloudAndLocation getImage(String id) {
            return new ImageInCloudAndLocation(
                this.swordComputeService.getImage(CloudScopedId.of(id).swordId()), cloud,
                credential);
        }

        @Nullable @Override public VirtualMachineInCloudAndLocation getVirtualMachine(String id) {
            return new VirtualMachineInCloudAndLocation(
                this.swordComputeService.getVirtualMachine(CloudScopedId.of(id).swordId()), cloud,
                credential);
        }

        @Nullable @Override public LocationInCloud getLocation(String id) {
            return new LocationInCloud(
                this.swordComputeService.getLocation(CloudScopedId.of(id).swordId()), cloud,
                credential);
        }

        @Nullable @Override public HardwareInCloudAndLocation getHardwareFlavor(String id) {
            return new HardwareInCloudAndLocation(
                this.swordComputeService.getHardwareFlavor(CloudScopedId.of(id).swordId()), cloud,
                credential);
        }

        @Override public Iterable<HardwareFlavor> listHardwareFlavors() {
            return Iterables
                .transform(swordComputeService.listHardwareFlavors(), hardwareFlavor -> {
                    if (hardwareFlavor instanceof SwordHardware) {
                        return new HardwareInCloudAndLocation((SwordHardware) hardwareFlavor, cloud,
                            credential);
                    } else {
                        throw new RuntimeException("Retrieved wrong hardware class.");
                    }
                });
        }

        @Override public Iterable<Image> listImages() {
            return Iterables.transform(swordComputeService.listImages(), image -> {
                if (image instanceof SwordImage) {
                    return new ImageInCloudAndLocation((SwordImage) image, cloud, credential);
                } else {
                    throw new RuntimeException("Retrieved wrong image type.");
                }
            });
        }

        @Override public Iterable<Location> listLocations() {
            return Iterables.transform(swordComputeService.listLocations(),
                location -> new LocationInCloud(location, cloud, credential));
        }

        @Override public Iterable<VirtualMachine> listVirtualMachines() {
            return Iterables
                .transform(swordComputeService.listVirtualMachines(), virtualMachine -> {
                    if (virtualMachine instanceof SwordVirtualMachine) {
                        return new VirtualMachineInCloudAndLocation(
                            (SwordVirtualMachine) virtualMachine, cloud, credential);
                    } else {
                        throw new RuntimeException("Retrieved wrong virtual machine type.");
                    }
                });
        }

        @Override public void deleteVirtualMachine(String id) {
            this.swordComputeService.deleteVirtualMachine(CloudScopedId.of(id).swordId());
        }

        @Override
        public VirtualMachine createVirtualMachine(VirtualMachineTemplate virtualMachineTemplate) {
            return new VirtualMachineInCloudAndLocation((SwordVirtualMachine) swordComputeService
                .createVirtualMachine(virtualMachineTemplate), cloud, credential);
        }

        @Override public SshConnection getSshConnection(HostAndPort hostAndPort) {
            return swordComputeService.getSshConnection(hostAndPort);
        }

        @Override public Optional<PublicIpService> getPublicIpService() {
            return swordComputeService.getPublicIpService();
        }

        @Override public Optional<KeyPairService> getKeyPairService() {
            return swordComputeService.getKeyPairService();
        }
    }
}
