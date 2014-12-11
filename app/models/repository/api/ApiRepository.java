package models.repository.api;

import com.google.inject.ImplementedBy;
import models.Api;
import models.repository.api.generic.NamedModelRepository;
import models.repository.impl.ApiRepositoryJpa;

/**
 * Created by daniel seybold on 11.12.2014.
 */
@ImplementedBy(ApiRepositoryJpa.class)
public interface ApiRepository extends NamedModelRepository<Api> {
}
