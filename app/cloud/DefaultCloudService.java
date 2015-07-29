package cloud;

import cloud.colosseum.BaseColosseumComputeService;
import cloud.colosseum.ColosseumComputeService;
import cloud.resources.HardwareInLocation;
import cloud.resources.ImageInLocation;
import cloud.resources.LocationInCloud;
import cloud.resources.VirtualMachineInLocation;
import com.google.inject.Inject;
import de.uniulm.omi.cloudiator.sword.api.service.DiscoveryService;
import models.CloudCredential;
import models.service.ModelService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 20.05.15.
 */
public class DefaultCloudService implements CloudService {

    private final ModelService<CloudCredential> cloudCredentialModelService;
    private final ComputeServiceFactory computeServiceFactory;

    @Inject public DefaultCloudService(ModelService<CloudCredential> cloudCredentialModelService,
        ComputeServiceFactory computeServiceFactory) {
        checkNotNull(cloudCredentialModelService);
        checkNotNull(computeServiceFactory);
        this.cloudCredentialModelService = cloudCredentialModelService;
        this.computeServiceFactory = computeServiceFactory;
    }

    @Override
    public DiscoveryService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> getDiscoveryService() {
        return new CompositeDiscoveryService(
            new BaseComputeServiceRegistry(computeServiceFactory, cloudCredentialModelService));
    }

    @Override public ColosseumComputeService computeService() {
        return new BaseColosseumComputeService(
            new BaseComputeServiceRegistry(computeServiceFactory, cloudCredentialModelService));
    }
}
