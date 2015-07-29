package cloud;

import cloud.resources.HardwareInLocation;
import cloud.resources.ImageInLocation;
import cloud.resources.LocationInCloud;
import cloud.resources.VirtualMachineInLocation;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import de.uniulm.omi.cloudiator.sword.service.ServiceBuilder;
import models.CloudCredential;
import play.Configuration;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by daniel on 19.06.15.
 */
public class SwordComputeServiceFactory implements ComputeServiceFactory {

    private String getNodeGroup() {
        //TODO: wrong place? config should probably checked during init of app
        return Configuration.root().getString("colosseum.nodegroup");
    }

    @Override
    public ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> computeService(
        CloudCredential cloudCredential) {

        checkNotNull(cloudCredential);

        return new DecoratingComputeService(ServiceBuilder
            .newServiceBuilder(cloudCredential.getCloud().getApi().getInternalProviderName())
            .endpoint(cloudCredential.getCloud().getEndpoint())
            .credentials(cloudCredential.getUser(), cloudCredential.getSecret())
            .nodeGroup(getNodeGroup()).build(), cloudCredential.getCloud().getUuid(),
            cloudCredential.getUuid());
    }

}
