package models.repository.api;

import com.google.inject.ImplementedBy;
import models.Cloud;
import models.CloudHardware;
import models.Hardware;
import models.repository.api.generic.ModelRepository;
import models.repository.impl.CloudHardwareFlavorRepositoryJpa;

/**
 * Created by daniel on 31.10.14.
 */
@ImplementedBy(CloudHardwareFlavorRepositoryJpa.class)
public interface CloudHardwareFlavorRepository extends ModelRepository<CloudHardware> {

    public CloudHardware findByCloudAndHardwareFlavor(Cloud cloud, Hardware hardware);

}
