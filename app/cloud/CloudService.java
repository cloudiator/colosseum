package cloud;

import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;

/**
 * Created by daniel on 20.05.15.
 */
public interface CloudService {

    ComputeService allComputeServices();

    ComputeService computeServiceForCloud(String cloud);

    ComputeService computeServiceForCloudCredential(String credential);

}
