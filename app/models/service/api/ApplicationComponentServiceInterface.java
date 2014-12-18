package models.service.api;

import com.google.inject.ImplementedBy;
import models.ApplicationComponent;
import models.service.api.generic.ModelServiceInterface;
import models.service.impl.ApplicationComponentService;

/**
 * Created by daniel seybold on 17.12.2014.
 */
@ImplementedBy(ApplicationComponentService.class)
public interface ApplicationComponentServiceInterface extends ModelServiceInterface<ApplicationComponent> {
}
