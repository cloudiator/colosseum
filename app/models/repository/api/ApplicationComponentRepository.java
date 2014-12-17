package models.repository.api;

import com.google.inject.ImplementedBy;
import models.ApplicationComponent;
import models.repository.api.generic.ModelRepository;
import models.repository.impl.ApplicationComponentRepositoryJpa;

/**
 * Created by daniel seybold on 17.12.2014.
 */
@ImplementedBy(ApplicationComponentRepositoryJpa.class)
public interface ApplicationComponentRepository extends ModelRepository<ApplicationComponent> {
}
