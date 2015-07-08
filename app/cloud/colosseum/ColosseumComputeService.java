package cloud.colosseum;

import cloud.resources.VirtualMachineInLocation;
import com.google.common.base.Optional;
import de.uniulm.omi.cloudiator.sword.api.extensions.KeyPairService;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;

/**
 * Created by daniel on 12.06.15.
 */
public interface ColosseumComputeService {

    VirtualMachineInLocation createVirtualMachine(
        ColosseumVirtualMachineTemplate virtualMachineTemplate);
    
    Optional<KeyPairService> getKeyPairService(String cloudUuid, String cloudCredentialUuid);

    Optional<PublicIpService> getPublicIpService(String cloudCredentialUuid);
}
