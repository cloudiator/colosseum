package models.service.impl;

import com.google.inject.Inject;
import models.Cloud;
import models.CloudHardware;
import models.Hardware;
import models.repository.api.CloudHardwareFlavorRepository;
import models.service.api.CloudHardwareFlavorServiceInterface;
import models.service.impl.generic.ModelService;

/**
 * Created by daniel on 03.11.14.
 */
public class CloudHardwareFlavorService extends ModelService<CloudHardware> implements CloudHardwareFlavorServiceInterface {

    @Inject
    public CloudHardwareFlavorService(CloudHardwareFlavorRepository cloudHardwareFlavorRepository) {
        super(cloudHardwareFlavorRepository);
    }

    @Override
    public CloudHardware getByCloudAndHardwareFlavor(final Cloud cloud, final Hardware hardware) {
        return ((CloudHardwareFlavorRepository) this.modelRepository).findByCloudAndHardwareFlavor(cloud, hardware);
    }
}
