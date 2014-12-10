package models.service.impl;

import com.google.inject.Inject;
import models.Location;
import models.repository.api.LocationRepository;
import models.service.api.LocationServiceInterface;
import models.service.impl.generic.ModelService;
import models.service.impl.generic.NamedModelService;

/**
 * Created by daniel seybold on 10.12.2014.
 */
public class LocationService extends NamedModelService<Location> implements LocationServiceInterface {

    @Inject
    public LocationService(LocationRepository locationRepository){super(locationRepository);}
}
