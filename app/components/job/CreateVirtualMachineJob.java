package components.job;

import cloud.colosseum.BaseColosseumVirtualMachineTemplate;
import cloud.colosseum.ColosseumComputeService;
import cloud.colosseum.ColosseumVirtualMachineTemplateBuilder;
import cloud.resources.VirtualMachineInLocation;
import com.google.common.base.Optional;
import components.installer.UnixInstaller;
import de.uniulm.omi.cloudiator.sword.api.exceptions.KeyPairException;
import de.uniulm.omi.cloudiator.sword.api.exceptions.PublicIpException;
import de.uniulm.omi.cloudiator.sword.api.extensions.KeyPairService;
import de.uniulm.omi.cloudiator.sword.api.extensions.PublicIpService;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;
import de.uniulm.omi.cloudiator.sword.core.domain.builders.TemplateOptionsBuilder;
import models.*;
import models.service.ModelService;

/**
 * Created by daniel on 08.05.15.
 */
public class CreateVirtualMachineJob extends GenericJob<VirtualMachine> {

    private final ModelService<KeyPair> keyPairModelService;

    public CreateVirtualMachineJob(VirtualMachine virtualMachine,
        ModelService<VirtualMachine> modelService, ModelService<Tenant> tenantModelService,
        ColosseumComputeService colosseumComputeService, Tenant tenant,
        ModelService<KeyPair> keyPairModelService) {
        super(virtualMachine, modelService, tenantModelService, colosseumComputeService, tenant);
        this.keyPairModelService = keyPairModelService;
    }

    @Override
    protected void doWork(VirtualMachine virtualMachine, ModelService<VirtualMachine> modelService,
        ColosseumComputeService computeService, Tenant tenant) throws JobException {

        //check keypair
        KeyPair keyPairToUse = null;
        for (KeyPair keyPair : keyPairModelService.getAll()) {
            if (keyPair.getCloud().equals(virtualMachine.cloud()) && keyPair.getTenant()
                .equals(tenant)) {
                keyPairToUse = keyPair;
                break;
            }
        }

        if (keyPairToUse == null) {
            Optional<KeyPairService> keyPairServiceOptional = computeService
                .getKeyPairService(virtualMachine.getCloudCredentials().get(0).getUuid());
            if (keyPairServiceOptional.isPresent()) {
                try {
                    final de.uniulm.omi.cloudiator.sword.api.domain.KeyPair remoteKeyPair =
                        keyPairServiceOptional.get().create(tenant.getUuid());
                    keyPairToUse = new KeyPair(virtualMachine.cloud(), tenant,
                        remoteKeyPair.privateKey().get(), remoteKeyPair.publicKey(),
                        remoteKeyPair.name());
                    this.keyPairModelService.save(keyPairToUse);
                } catch (KeyPairException e) {
                    throw new JobException(e);
                }
            }
        }

        ColosseumVirtualMachineTemplateBuilder builder =
            BaseColosseumVirtualMachineTemplate.builder();
        if (keyPairToUse != null) {
            builder.templateOptions(
                TemplateOptionsBuilder.newBuilder().keyPairName(keyPairToUse.getRemoteId())
                    .build());
        }
        VirtualMachineInLocation cloudVirtualMachine = computeService
            .createVirtualMachine(builder.virtualMachineModel(virtualMachine).build());
        virtualMachine.setRemoteId(cloudVirtualMachine.id());
        for (String ip : cloudVirtualMachine.privateAddresses()) {
            virtualMachine.addIpAddress(new IpAddress(virtualMachine, ip, IpType.PRIVATE));
        }
        for (String ip : cloudVirtualMachine.publicAddresses()) {
            virtualMachine.addIpAddress(new IpAddress(virtualMachine, ip, IpType.PUBLIC));
        }
        modelService.save(virtualMachine);

        if (virtualMachine.publicIpAddress() == null) {
            final Optional<PublicIpService> publicIpService = computeService
                .getPublicIpService(virtualMachine.getCloudCredentials().get(0).getUuid());
            if (publicIpService.isPresent()) {
                try {
                    final String publicIp =
                        publicIpService.get().addPublicIp(virtualMachine.getRemoteId());
                    virtualMachine
                        .addIpAddress(new IpAddress(virtualMachine, publicIp, IpType.PUBLIC));
                } catch (PublicIpException e) {
                    throw new JobException(e);
                }
            } else {
                throw new JobException(
                    "VirtualMachine started without public IP and IpService is not available.");
            }
        }

        final RemoteConnection remoteConnection =
            computeService.remoteConnection(tenant, virtualMachine);

        UnixInstaller unixInstaller = new UnixInstaller(remoteConnection, "ubuntu");
        unixInstaller.installAll();
    }
}
