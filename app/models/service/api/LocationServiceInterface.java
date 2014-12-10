package models.service.api;

import com.google.inject.ImplementedBy;
import models.Location;
import models.service.api.generic.ModelServiceInterface;
import models.service.api.generic.NamedModelServiceInterface;
import models.service.impl.LocationService;

/**
 * Created by daniel seybold on 10.12.2014.
 */
@ImplementedBy(LocationService.class)
public interface LocationServiceInterface extends NamedModelServiceInterface<Location> {
}
