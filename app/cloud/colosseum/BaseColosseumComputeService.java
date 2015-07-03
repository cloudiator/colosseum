package cloud.colosseum;

import cloud.ComputeServiceRegistry;
import cloud.resources.VirtualMachineInLocation;
import com.google.common.base.Optional;
import de.uniulm.omi.cloudiator.sword.api.extensions.KeyPairService;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;

/**
 * Created by daniel on 21.06.15.
 */
public class BaseColosseumComputeService implements ColosseumComputeService {

    private final ComputeServiceRegistry computeServices;

    public BaseColosseumComputeService(ComputeServiceRegistry computeServices) {
        this.computeServices = computeServices;
    }

    @Override public VirtualMachineInLocation createVirtualMachine(
        ColosseumVirtualMachineTemplate virtualMachineTemplate) {
        return this.computeServices
            .getComputeService(virtualMachineTemplate.getCloudCredentialUuid())
            .createVirtualMachine(virtualMachineTemplate);
    }

    @Override public Optional<KeyPairService> getKeyPairService(String cloudCredentialUuid) {
        //todo: decorate
        return this.computeServices.getComputeService(cloudCredentialUuid).getKeyPairService();
    }

    @Override public Optional<PublicIpService> getPublicIpService(String cloudCredentialUuid) {
        //todo: decorate
        return this.computeServices.getComputeService(cloudCredentialUuid).getPublicIpService();
    }
}
