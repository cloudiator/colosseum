package models.service.api;

import com.google.inject.ImplementedBy;
import models.Component;
import models.service.api.generic.NamedModelServiceInterface;
import models.service.impl.ComponentService;

/**
 * Created by daniel seybold on 16.12.2014.
 */
@ImplementedBy(ComponentService.class)
public interface ComponentServiceInterface extends NamedModelServiceInterface<Component> {
}
