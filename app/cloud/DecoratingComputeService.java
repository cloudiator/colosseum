package cloud;

import cloud.resources.HardwareInLocation;
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

/**
 * Created by daniel on 28.05.15.
 */
public class DecoratingComputeService implements ComputeService {

    private final ComputeService delegate;
    private final String cloudId;
    private final String cloudCredential;


    public DecoratingComputeService(ComputeService delegate, String cloudId,
        String cloudCredential) {
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
                @Nullable @Override public HardwareInLocation apply(HardwareFlavor hardwareFlavor) {
                    String location = IdScopeByLocations.from(hardwareFlavor.id()).getLocationId();
                    return new HardwareInLocation(hardwareFlavor, cloudId, cloudCredential);
                }
            });
    }

    @Override public Iterable<Image> listImages() {
        return null;
    }

    @Override public Iterable<Location> listLocations() {
        return null;
    }

    @Override public Iterable<VirtualMachine> listVirtualMachines() {
        return null;
    }

    @Override public void deleteVirtualMachine(String s) {

    }

    @Override
    public VirtualMachine createVirtualMachine(VirtualMachineTemplate virtualMachineTemplate) {
        return null;
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
}
