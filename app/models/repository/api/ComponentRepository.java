package models.repository.api;

import com.google.inject.ImplementedBy;
import models.Component;
import models.repository.api.generic.NamedModelRepository;
import models.repository.impl.ComponentRepositoryJpa;

/**
 * Created by daniel seybold on 16.12.2014.
 */
@ImplementedBy(ComponentRepositoryJpa.class)
public interface ComponentRepository extends NamedModelRepository<Component> {
}
