package models.service.api;

import com.google.inject.ImplementedBy;
import models.Instance;
import models.service.api.generic.ModelServiceInterface;
import models.service.impl.InstanceService;

/**
 * Created by daniel seybold on 17.12.2014.
 */
@ImplementedBy(InstanceService.class)
public interface InstanceServiceInterface extends ModelServiceInterface<Instance> {
}
