package models.repository.api;

import com.google.inject.ImplementedBy;
import models.Location;
import models.repository.api.generic.NamedModelRepository;
import models.repository.impl.LocationRepositoryJpa;

/**
 * Created by daniel seybold on 10.12.2014.
 */
@ImplementedBy(LocationRepositoryJpa.class)
public interface LocationRepository extends NamedModelRepository<Location> {
}
