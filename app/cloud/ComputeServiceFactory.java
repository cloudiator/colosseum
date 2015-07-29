package cloud;

import cloud.resources.HardwareInLocation;
import cloud.resources.ImageInLocation;
import cloud.resources.LocationInCloud;
import cloud.resources.VirtualMachineInLocation;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import de.uniulm.omi.cloudiator.sword.api.service.DiscoveryService;
import models.CloudCredential;

/**
 * Created by daniel on 19.06.15.
 */
public interface ComputeServiceFactory {
    ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> computeService(
        CloudCredential cloudCredential);
}
