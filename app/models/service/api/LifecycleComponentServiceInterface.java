package models.service.api;

import com.google.inject.ImplementedBy;
import models.LifecycleComponent;
import models.service.api.generic.NamedModelServiceInterface;
import models.service.impl.LifecycleComponentService;

/**
 * Created by daniel seybold on 16.12.2014.
 */
@ImplementedBy(LifecycleComponentService.class)
public interface LifecycleComponentServiceInterface extends NamedModelServiceInterface<LifecycleComponent> {
}
