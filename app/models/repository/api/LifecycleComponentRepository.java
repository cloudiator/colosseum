package models.repository.api;

import com.google.inject.ImplementedBy;
import models.LifecycleComponent;
import models.repository.api.generic.NamedModelRepository;
import models.repository.impl.LifecycleComponentRepositoryJpa;

/**
 * Created by daniel seybold on 16.12.2014.
 */
@ImplementedBy(LifecycleComponentRepositoryJpa.class)
public interface LifecycleComponentRepository extends NamedModelRepository<LifecycleComponent> {
}
