package cloud;

import cloud.resources.HardwareInLocation;
import cloud.resources.ImageInLocation;
import cloud.resources.LocationInCloud;
import cloud.resources.VirtualMachineInLocation;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.net.HostAndPort;
import de.uniulm.omi.cloudiator.sword.api.domain.*;
import de.uniulm.omi.cloudiator.sword.api.extensions.KeyPairService;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import de.uniulm.omi.cloudiator.sword.api.ssh.SshConnection;
import de.uniulm.omi.cloudiator.sword.core.util.IdScopeByLocations;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 28.05.15.
 */
public class DecoratingComputeService implements ComputeService {

    private final ComputeService delegate;
    private final String cloudId;
    private final String cloudCredential;


    public DecoratingComputeService(ComputeService delegate, String cloudId,
        String cloudCredential) {

        checkNotNull(delegate);
        checkNotNull(cloudId);
        checkArgument(!cloudId.isEmpty());
        checkNotNull(cloudCredential);
        checkArgument(!cloudCredential.isEmpty());

        this.delegate = delegate;
        this.cloudCredential = cloudCredential;
        this.cloudId = cloudId;
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
        return Iterables.transform(delegate.listHardwareFlavors(),
            new Function<HardwareFlavor, HardwareInLocation>() {
                @Nullable @Override
                public HardwareInLocation apply(@Nullable HardwareFlavor hardwareFlavor) {
                    checkNotNull(hardwareFlavor);
                    String location = IdScopeByLocations.from(hardwareFlavor.id()).getLocationId();
                    return new HardwareInLocation(hardwareFlavor, cloudId, cloudCredential,
                        location);
                }
            });
    }

    @Override public Iterable<Image> listImages() {
        return Iterables.transform(delegate.listImages(), new Function<Image, Image>() {
            @Nullable @Override public Image apply(@Nullable Image image) {
                checkNotNull(image);
                String location = IdScopeByLocations.from(image.id()).getLocationId();
                return new ImageInLocation(image, cloudId, cloudCredential, location);
            }
        });
    }

    @Override public Iterable<Location> listLocations() {
        return Iterables.transform(delegate.listLocations(), new Function<Location, Location>() {
            @Nullable @Override public Location apply(@Nullable Location location) {
                checkNotNull(location);
                return new LocationInCloud(location, cloudId, cloudCredential);
            }
        });
    }

    @Override public Iterable<VirtualMachine> listVirtualMachines() {
        return Iterables.transform(delegate.listVirtualMachines(), new VirtualMachineDecorator());
    }

    @Override public void deleteVirtualMachine(String s) {

    }

    @Override
    public VirtualMachine createVirtualMachine(VirtualMachineTemplate virtualMachineTemplate) {
        VirtualMachine virtualMachine = delegate.createVirtualMachine(virtualMachineTemplate);
        return new VirtualMachineDecorator().apply(virtualMachine);
    }

    @Override public SshConnection getSshConnection(HostAndPort hostAndPort) {
        return delegate.getSshConnection(hostAndPort);
    }

    @Override public Optional<PublicIpService> getPublicIpService() {
        return delegate.getPublicIpService();
    }

    @Override public Optional<KeyPairService> getKeyPairService() {
        return delegate.getKeyPairService();
    }


    private class VirtualMachineDecorator implements Function<VirtualMachine, VirtualMachine> {
        @Nullable @Override public VirtualMachine apply(@Nullable VirtualMachine virtualMachine) {
            checkNotNull(virtualMachine);
            String location = IdScopeByLocations.from(virtualMachine.id()).getLocationId();
            return new VirtualMachineInLocation(virtualMachine, cloudId, cloudCredential, location);
        }
    }
}
