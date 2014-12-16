package models.repository.api;

import com.google.inject.ImplementedBy;
import models.Application;
import models.repository.api.generic.NamedModelRepository;
import models.repository.impl.ApplicationRepositoryJpa;

/**
 * Created by daniel seybold on 16.12.2014.
 */
@ImplementedBy(ApplicationRepositoryJpa.class)
public interface ApplicationRepository extends NamedModelRepository<Application> {
}
