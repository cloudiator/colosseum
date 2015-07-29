package cloud;

import cloud.colosseum.ColosseumComputeService;
import cloud.resources.HardwareInLocation;
import cloud.resources.ImageInLocation;
import cloud.resources.LocationInCloud;
import cloud.resources.VirtualMachineInLocation;
import de.uniulm.omi.cloudiator.sword.api.service.DiscoveryService;


/**
 * Created by daniel on 20.05.15.
 */
public interface CloudService {

    DiscoveryService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> getDiscoveryService();

    ColosseumComputeService computeService();

}
