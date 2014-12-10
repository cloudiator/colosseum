package models.repository.api;

import com.google.inject.ImplementedBy;
import models.OperatingSystem;
import models.repository.api.generic.ModelRepository;
import models.repository.impl.OperatingSystemRepositoryJpa;

/**
 * Created by daniel seybold on 10.12.2014.
 */
@ImplementedBy(OperatingSystemRepositoryJpa.class)
public interface OperatingSystemRepository extends ModelRepository<OperatingSystem> {
}
