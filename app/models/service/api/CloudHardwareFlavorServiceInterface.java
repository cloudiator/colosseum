package models.service.api;

import com.google.inject.ImplementedBy;
import models.Cloud;
import models.CloudHardware;
import models.Hardware;
import models.service.api.generic.ModelServiceInterface;
import models.service.impl.CloudHardwareFlavorService;

/**
 * Created by daniel on 03.11.14.
 */
@ImplementedBy(CloudHardwareFlavorService.class)
public interface CloudHardwareFlavorServiceInterface extends ModelServiceInterface<CloudHardware>{

    public CloudHardware getByCloudAndHardwareFlavor(Cloud cloud, Hardware hardware);


}
