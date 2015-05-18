package cloud.sword;

import com.google.common.base.Optional;
import de.uniulm.omi.cloudiator.sword.api.domain.LoginCredential;
import de.uniulm.omi.cloudiator.sword.api.domain.VirtualMachine;
import de.uniulm.omi.cloudiator.sword.core.util.IdScopeByLocations;

import java.util.Set;

/**
 * Created by daniel on 28.04.15.
 */
public class SwordVirtualMachine implements VirtualMachine, ResourceInLocation {

    private final VirtualMachine virtualMachine;

    SwordVirtualMachine(VirtualMachine virtualMachine) {
        this.virtualMachine = virtualMachine;
    }

    @Override public String location() {
        return IdScopeByLocations.from(virtualMachine.id()).getLocationId();
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

    @Override public String id() {
        return virtualMachine.id();
    }

    @Override public String name() {
        return virtualMachine.name();
    }
}
