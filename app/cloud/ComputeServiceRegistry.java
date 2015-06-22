package cloud;

import cloud.resources.HardwareInLocation;
import cloud.resources.ImageInLocation;
import cloud.resources.LocationInCloud;
import cloud.resources.VirtualMachineInLocation;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import models.CloudCredential;

import java.util.List;
import java.util.Set;

/**
 * Created by daniel on 21.06.15.
 */
public interface ComputeServiceRegistry extends
    Iterable<ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation>> {

    Set<ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation>> getComputeServices();

    ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> getComputeService(
        String cloudCredentialUuid);

    Set<ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation>> getComputeServices(
        List<CloudCredential> cloudCredentials);
}
