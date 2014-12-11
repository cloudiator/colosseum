package models.repository.api;

import com.google.inject.ImplementedBy;
import models.CloudApi;
import models.repository.api.generic.ModelRepository;
import models.repository.impl.CloudApiRepositoryJpa;

/**
 * Created by daniel seybold on 11.12.2014.
 */
@ImplementedBy(CloudApiRepositoryJpa.class)
public interface CloudApiRepository extends ModelRepository<CloudApi> {
}
