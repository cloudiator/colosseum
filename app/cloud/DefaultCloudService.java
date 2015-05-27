package cloud;

import cloud.sword.SwordComputeService;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import models.CloudCredential;
import models.service.api.generic.ModelService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by daniel on 20.05.15.
 */
public class DefaultCloudService implements CloudService {

    private static class ComputeServiceHolder {

        private final ModelService<CloudCredential> cloudCredentialModelService;
        private Map<String, ComputeService> computeServices;

        private ComputeServiceHolder(ModelService<CloudCredential> cloudCredentialModelService) {
            this.cloudCredentialModelService = cloudCredentialModelService;
            computeServices = new HashMap<>();
        }

        private SwordComputeService createComputeService(CloudCredential cloudCredential) {
            //TODO: validate for possible nulls, if user has not finished creating all stuff
            return new SwordComputeService(
                cloudCredential.getCloud().getApi().getInternalProviderName(),
                cloudCredential.getCloud().getEndpoint(), cloudCredential.getUser(),
                cloudCredential.getSecret());
        }

        private synchronized void update() {
            for (CloudCredential cloudCredential : cloudCredentialModelService.getAll()) {
                if (!computeServices.containsKey(cloudCredential.getUuid())) {
                    this.computeServices.put(cloudCredential.getUuid(),
                        new CloudAndCredentialAwareComputeService(
                            cloudCredential.getCloud().getUuid(), cloudCredential.getUuid(),
                            createComputeService(cloudCredential)));
                }
            }
        }

        public Iterable<ComputeService> getComputeServices() {
            update();
            return new ArrayList<>(computeServices.values());
        }

    }

    @Override public ComputeService allComputeServices() {
        return null;
    }

    @Override public ComputeService computeServiceForCloud(String cloud) {
        return null;
    }

    @Override public ComputeService computeServiceForCloudCredential(String credential) {
        return null;
    }
}
