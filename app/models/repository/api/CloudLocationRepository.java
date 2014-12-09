package models.repository.api;

import com.google.inject.ImplementedBy;
import models.CloudLocation;
import models.repository.api.generic.ModelRepository;
import models.repository.impl.CloudLocationRepositoryJpa;

/**
 * Created by bwpc on 09.12.2014.
 */
@ImplementedBy(CloudLocationRepositoryJpa.class)
public interface CloudLocationRepository extends ModelRepository<CloudLocation> {
}
