package cloud.sword;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.net.HostAndPort;
import de.uniulm.omi.cloudiator.sword.api.domain.*;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import de.uniulm.omi.cloudiator.sword.api.ssh.SshConnection;
import de.uniulm.omi.cloudiator.sword.core.domain.builders.LoginCredentialBuilder;
import de.uniulm.omi.cloudiator.sword.service.ServiceBuilder;

import javax.annotation.Nullable;

/**
 * Created by daniel on 28.04.15.
 */
public class SwordComputeService implements ComputeService {

    private final ComputeService computeService;

    public SwordComputeService(String providerName, String endpoint, String username,
        String password) {
        this.computeService = ServiceBuilder.newServiceBuilder(providerName).endpoint(endpoint)
            .credentials(username, password).nodeGroup("geagzjkue").loginCredentials(
                LoginCredentialBuilder.newBuilder().username("ubuntu").password("test").build())
            .loggingModule(new SwordLoggingModule()).build();
    }

    @Nullable @Override public SwordImage getImage(String id) {
        return new SwordImage(computeService.getImage(id));
    }

    @Nullable @Override public SwordVirtualMachine getVirtualMachine(String id) {
        return new SwordVirtualMachine(computeService.getVirtualMachine(id));
    }

    @Nullable @Override public Location getLocation(String id) {
        return computeService.getLocation(id);
    }

    @Nullable @Override public SwordHardware getHardwareFlavor(String id) {
        return new SwordHardware(computeService.getHardwareFlavor(id));
    }

    @Override public Iterable<HardwareFlavor> listHardwareFlavors() {
        return Iterables.transform(computeService.listHardwareFlavors(), SwordHardware::new);
    }

    @Override public Iterable<Image> listImages() {
        return Iterables.transform(computeService.listImages(), SwordImage::new);
    }

    @Override public Iterable<Location> listLocations() {
        return computeService.listLocations();
    }

    @Override public Iterable<VirtualMachine> listVirtualMachines() {
        return Iterables.transform(computeService.listVirtualMachines(), SwordVirtualMachine::new);
    }

    @Override public void deleteVirtualMachine(String id) {
        computeService.deleteVirtualMachine(id);
    }

    @Override
    public VirtualMachine createVirtualMachine(VirtualMachineTemplate virtualMachineTemplate) {
        return new SwordVirtualMachine(computeService.createVirtualMachine(virtualMachineTemplate));
    }

    @Override public SshConnection getSshConnection(HostAndPort hostAndPort) {
        return this.computeService.getSshConnection(hostAndPort);
    }

    @Override public Optional<PublicIpService> getPublicIpService() {
        return this.computeService.getPublicIpService();
    }
}
