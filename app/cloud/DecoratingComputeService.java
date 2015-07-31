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
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import de.uniulm.omi.cloudiator.sword.api.service.ConnectionService;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 28.05.15.
 */
public class DecoratingComputeService implements
    ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> {

    private final ComputeService<HardwareFlavor, Image, Location, VirtualMachine> delegate;
    private final String cloudId;
    private final String cloudCredential;


    public DecoratingComputeService(
        ComputeService<HardwareFlavor, Image, Location, VirtualMachine> delegate, String cloudId,
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
        return Iterables.transform(delegate.listVirtualMachines(), new VirtualMachineDecorator());
    }

    @Override public void deleteVirtualMachine(String s) {
        //TODO: implement this.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override public VirtualMachineInLocation createVirtualMachine(
        VirtualMachineTemplate virtualMachineTemplate) {
        return new VirtualMachineDecorator()
            .apply(this.delegate.createVirtualMachine(virtualMachineTemplate));
    }

    @Override public ConnectionService getConnectionService() {
        return this.delegate.getConnectionService();
    }

    @Override public Optional<PublicIpService> getPublicIpService() {
        return this.delegate.getPublicIpService();
    }

    @Override public Optional<KeyPairService> getKeyPairService() {
        return this.delegate.getKeyPairService();
    }

    private class VirtualMachineDecorator
        implements Function<VirtualMachine, VirtualMachineInLocation> {
        @Nullable @Override
        public VirtualMachineInLocation apply(@Nullable VirtualMachine virtualMachine) {
            checkNotNull(virtualMachine);
            return new VirtualMachineInLocation(virtualMachine, cloudId, cloudCredential);
        }
    }
}
