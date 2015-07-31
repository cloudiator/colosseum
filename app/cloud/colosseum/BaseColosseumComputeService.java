package cloud.colosseum;

import cloud.ComputeServiceRegistry;
import cloud.DecoratingPublicIpService;
import cloud.resources.HardwareInLocation;
import cloud.resources.ImageInLocation;
import cloud.resources.LocationInCloud;
import cloud.resources.VirtualMachineInLocation;
import com.google.common.base.Optional;
import com.google.common.net.HostAndPort;
import de.uniulm.omi.cloudiator.sword.api.domain.OSFamily;
import de.uniulm.omi.cloudiator.sword.api.extensions.KeyPairService;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import de.uniulm.omi.cloudiator.sword.api.service.ConnectionService;
import de.uniulm.omi.cloudiator.sword.core.domain.builders.LoginCredentialBuilder;
import models.KeyPair;
import models.Tenant;
import models.VirtualMachine;

import java.util.Iterator;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

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

    private ConnectionService connectionService() {
        //todo: ssh connection in sword should be available without a compute service
        final Iterator<ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation>>
            iterator = computeServices.getComputeServices().iterator();
        checkState(iterator.hasNext(),
            "Accessing connection service is not possible without configured compute services.");
        return iterator.next().getConnectionService();

    }

    @Override
    public RemoteConnection remoteConnection(Tenant tenant, VirtualMachine virtualMachine) {
        checkNotNull(tenant);
        checkNotNull(virtualMachine);

        final LoginCredentialBuilder loginCredentialBuilder = LoginCredentialBuilder.newBuilder();

        KeyPair keyPairToUse = null;
        for (KeyPair possibleMatch : tenant.getKeyPairs()) {
            if (possibleMatch.getCloud().equals(virtualMachine.cloud())) {
                keyPairToUse = possibleMatch;
            }
        }

        if (keyPairToUse != null && keyPairToUse.getPrivateKey() != null) {
            loginCredentialBuilder.privateKey(keyPairToUse.getPrivateKey());
        } else if (virtualMachine.getLoginPassword() != null) {
            loginCredentialBuilder.password(virtualMachine.getLoginPassword());
        } else {
            throw new IllegalArgumentException(
                "PrivateKey nor LoginPassword available for virtual machine.");
        }

        checkNotNull(virtualMachine.publicIpAddress(),
            "Virtual machine has no public ip address assigned.");
        //noinspection ConstantConditions
        final HostAndPort hostAndPort =
            HostAndPort.fromParts(virtualMachine.publicIpAddress().getIp(), 22);

        // todo: remove hardcoded reference to unix.
        return connectionService()
            .getRemoteConnection(hostAndPort, OSFamily.UNIX, loginCredentialBuilder.build());
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
