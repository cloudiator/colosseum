package cloud.colosseum;

import de.uniulm.omi.cloudiator.sword.api.domain.VirtualMachineTemplate;

/**
 * Created by daniel on 19.06.15.
 */
public interface ColosseumVirtualMachineTemplate extends VirtualMachineTemplate {
    
    String cloudUuid();

    String cloudCredentialUuid();
}
