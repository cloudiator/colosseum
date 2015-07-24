package cloud.colosseum;

import cloud.ComputeServiceRegistry;
import cloud.DecoratingPublicIpService;
import cloud.resources.VirtualMachineInLocation;
import com.google.common.base.Optional;
import com.google.common.net.HostAndPort;
import de.uniulm.omi.cloudiator.sword.api.domain.OSFamily;
import de.uniulm.omi.cloudiator.sword.api.extensions.KeyPairService;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;
import de.uniulm.omi.cloudiator.sword.core.domain.builders.LoginCredentialBuilder;
import models.KeyPair;
import models.Tenant;
import models.VirtualMachine;

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
        return this.computeServices.getComputeService(virtualMachineTemplate.cloudCredentialUuid())
            .createVirtualMachine(virtualMachineTemplate);
    }

    @Override
    public RemoteConnection remoteConnection(Tenant tenant, VirtualMachine virtualMachine) {
        //todo: currently hacked for keypair and ubuntu, needs more generic approach
        //todo: osfamily should be resolvable from virtual machine...
        //todo: maybe the image property should have a login name.
        //todo: ssh connection in sword should be available without a compute service
        final HostAndPort hostAndPort =
            HostAndPort.fromParts(virtualMachine.publicIpAddress().getIp(), 22);

        KeyPair keyPairToUse = null;
        for (KeyPair possibleMatch : tenant.getKeyPairs()) {
            if (possibleMatch.getCloud().equals(virtualMachine.cloud())) {
                keyPairToUse = possibleMatch;
            }
        }

        return this.computeServices.getComputeServices().iterator().next()
            .getRemoteConnection(hostAndPort, OSFamily.UNIX,
                LoginCredentialBuilder.newBuilder().username("ubuntu")
                    .privateKey(keyPairToUse.getPrivateKey()).build());
    }

    @Override public Optional<KeyPairService> getKeyPairService(String cloudCredentialUuid) {
        //todo: decorate

        return this.computeServices.getComputeService(cloudCredentialUuid).getKeyPairService();
    }

    @Override public Optional<PublicIpService> getPublicIpService(String cloudCredentialUuid) {
        Optional<PublicIpService> publicIpService =
            computeServices.getComputeService(cloudCredentialUuid).getPublicIpService();
        if (!publicIpService.isPresent()) {
            return Optional.absent();
        }
        return Optional.of(new DecoratingPublicIpService(publicIpService.get()));
    }
}
