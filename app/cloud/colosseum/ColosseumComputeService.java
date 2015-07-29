package cloud.colosseum;

import cloud.resources.VirtualMachineInLocation;
import com.google.common.base.Optional;
import de.uniulm.omi.cloudiator.sword.api.extensions.KeyPairService;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;
import models.Tenant;
import models.VirtualMachine;

/**
 * Created by daniel on 12.06.15.
 */
public interface ColosseumComputeService {

    VirtualMachineInLocation createVirtualMachine(
        ColosseumVirtualMachineTemplate virtualMachineTemplate);

    //todo: check if virtual machine should be closed to a tenant
    RemoteConnection remoteConnection(Tenant tenant, VirtualMachine virtualMachine);

    Optional<KeyPairService> getKeyPairService(String cloudCredentialUuid);

    Optional<PublicIpService> getPublicIpService(String cloudCredentialUuid);
}
