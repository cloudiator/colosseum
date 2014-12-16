package models.service.api;

import com.google.inject.ImplementedBy;
import models.Application;
import models.service.api.generic.NamedModelServiceInterface;
import models.service.impl.ApplicationService;

/**
 * Created by daniel seybold on 16.12.2014.
 */
@ImplementedBy(ApplicationService.class)
public interface ApplicationServiceInterface extends NamedModelServiceInterface<Application>{
}
