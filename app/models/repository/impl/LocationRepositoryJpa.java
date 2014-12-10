package models.repository.impl;

import models.Location;
import models.repository.api.LocationRepository;
import models.repository.impl.generic.ModelRepositoryJpa;
import models.repository.impl.generic.NamedModelRepositoryJpa;

/**
 * Created by daniel seybold on 10.12.2014.
 */
public class LocationRepositoryJpa extends NamedModelRepositoryJpa<Location> implements LocationRepository {
}
