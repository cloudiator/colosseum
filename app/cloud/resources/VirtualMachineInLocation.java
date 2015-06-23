package cloud.resources;

import com.google.common.base.Optional;
import de.uniulm.omi.cloudiator.sword.api.domain.LoginCredential;
import de.uniulm.omi.cloudiator.sword.api.domain.VirtualMachine;

import java.util.Set;

/**
 * Created by daniel on 28.05.15.
 */
public class VirtualMachineInLocation extends AbstractLocationScopedResource<VirtualMachine>
    implements VirtualMachine {

    private final VirtualMachine virtualMachine;

    public VirtualMachineInLocation(VirtualMachine resource, String cloud, String credential) {
        super(resource, cloud, credential);
        this.virtualMachine = resource;
    }

    @Override public Set<String> publicAddresses() {
        return virtualMachine.publicAddresses();
    }

    @Override public Set<String> privateAddresses() {
        return virtualMachine.privateAddresses();
    }

    @Override public Optional<LoginCredential> loginCredential() {
        return virtualMachine.loginCredential();
    }
}
