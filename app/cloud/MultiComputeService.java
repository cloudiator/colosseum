package cloud;

import cloud.sword.SwordComputeService;
import cloud.sword.SwordHardware;
import cloud.sword.SwordImage;
import cloud.sword.SwordVirtualMachine;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.net.HostAndPort;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.uniulm.omi.cloudiator.sword.api.domain.*;
import de.uniulm.omi.cloudiator.sword.api.extensions.KeyPairService;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import de.uniulm.omi.cloudiator.sword.api.ssh.SshConnection;
import models.CloudCredential;
import models.service.api.generic.ModelService;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by daniel on 17.04.15.
 */
@Singleton public class MultiComputeService implements ComputeService {


    private final ComputeServiceHolder computeServiceHolder;

    @Inject public MultiComputeService(ModelService<CloudCredential> cloudCredentialModelService) {
        this.computeServiceHolder = new ComputeServiceHolder(cloudCredentialModelService);
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
        for (ComputeService computeService : computeServiceHolder.getComputeServices()) {
            for (HardwareFlavor hardwareFlavor : computeService.listHardwareFlavors()) {
                hardwareFlavors.add(hardwareFlavor);
            }
        }
        return hardwareFlavors;
    }

    @Override public Iterable<Image> listImages() {
        List<Image> images = new LinkedList<>();
        for (ComputeService computeService : computeServiceHolder.getComputeServices()) {
            for (Image image : computeService.listImages()) {
                images.add(image);
            }
        }
        return images;
    }

    @Override public Iterable<Location> listLocations() {
        List<Location> locations = new LinkedList<>();
        for (ComputeService computeService : computeServiceHolder.getComputeServices()) {
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
            for (ComputeService computeService : computeServiceHolder.getComputeServices()) {
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

    private static class ComputeServiceHolder {

        private final ModelService<CloudCredential> cloudCredentialModelService;
        private Map<String, ComputeService> computeServices;

        private ComputeServiceHolder(ModelService<CloudCredential> cloudCredentialModelService) {
            this.cloudCredentialModelService = cloudCredentialModelService;
            computeServices = new HashMap<>();
        }

        private SwordComputeService createComputeService(CloudCredential cloudCredential) {
            //TODO: validate for possible nulls, if user has not finished creating all stuff
            return new SwordComputeService(
                cloudCredential.getCloud().getApi().getInternalProviderName(),
                cloudCredential.getCloud().getEndpoint(), cloudCredential.getUser(),
                cloudCredential.getSecret());
        }

        private synchronized void update() {
            for (CloudCredential cloudCredential : cloudCredentialModelService.getAll()) {
                if (!computeServices.containsKey(cloudCredential.getUuid())) {
                    this.computeServices.put(cloudCredential.getUuid(),
                        new CloudAndCredentialAwareComputeService(
                            cloudCredential.getCloud().getUuid(), cloudCredential.getUuid(),
                            createComputeService(cloudCredential)));
                }
            }
        }

        public Iterable<ComputeService> getComputeServices() {
            update();
            return new ArrayList<>(computeServices.values());
        }

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
                this.swordComputeService.getImage(CloudCredentialLocationId.of(id).swordId()),
                cloud, credential);
        }

        @Nullable @Override public VirtualMachineInCloudAndLocation getVirtualMachine(String id) {
            return new VirtualMachineInCloudAndLocation(this.swordComputeService
                .getVirtualMachine(CloudCredentialLocationId.of(id).swordId()), cloud, credential);
        }

        @Nullable @Override public LocationInCloud getLocation(String id) {
            return new LocationInCloud(
                this.swordComputeService.getLocation(CloudCredentialLocationId.of(id).swordId()),
                cloud, credential);
        }

        @Nullable @Override public HardwareInCloudAndLocation getHardwareFlavor(String id) {
            return new HardwareInCloudAndLocation(this.swordComputeService
                .getHardwareFlavor(CloudCredentialLocationId.of(id).swordId()), cloud, credential);
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
            this.swordComputeService
                .deleteVirtualMachine(CloudCredentialLocationId.of(id).swordId());
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
