package models.service.api;

import com.google.inject.ImplementedBy;
import models.Cloud;
import models.service.api.generic.NamedModelServiceInterface;
import models.service.impl.CloudService;

/**
 * Created by daniel on 31.10.14.
 */
@ImplementedBy(CloudService.class)
public interface CloudServiceInterface extends NamedModelServiceInterface<Cloud>{

}
