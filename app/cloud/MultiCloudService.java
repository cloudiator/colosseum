package cloud;

import com.google.common.base.Optional;
import com.google.common.net.HostAndPort;
import de.uniulm.omi.cloudiator.sword.api.domain.VirtualMachineTemplate;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;
import de.uniulm.omi.cloudiator.sword.api.ssh.SshConnection;

import javax.annotation.Nullable;

/**
 * Created by daniel on 17.04.15.
 */
public class MultiCloudService implements CloudService {


    public MultiCloudService(CloudServiceFactory cloudServiceFactory) {

    }

    @Nullable @Override public ImageInCloudAndLocation getImage(String s) {
        return null;
    }

    @Nullable @Override public VirtualMachineInCloudAndLocation getVirtualMachine(String s) {
        return null;
    }

    @Nullable @Override public LocationInCloud getLocation(String s) {
        return null;
    }

    @Nullable @Override public HardwareInCloudAndLocation getHardware(String s) {
        return null;
    }

    @Override public Iterable<HardwareInCloudAndLocation> listHardware() {
        return null;
    }

    @Override public Iterable<ImageInCloudAndLocation> listImages() {
        return null;
    }

    @Override public Iterable<LocationInCloud> listLocations() {
        return null;
    }

    @Override public Iterable<VirtualMachineInCloudAndLocation> listVirtualMachines() {
        return null;
    }

    @Override public void deleteVirtualMachine(String s) {

    }

    @Override public VirtualMachineInCloudAndLocation createVirtualMachine(
        VirtualMachineTemplate virtualMachineTemplate) {
        return null;
    }

    @Override public SshConnection getSshConnection(HostAndPort hostAndPort) {
        return null;
    }

    @Override public Optional<PublicIpService> getPublicIpService() {
        return null;
    }
}
