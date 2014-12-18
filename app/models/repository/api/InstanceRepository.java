package models.repository.api;

import com.google.inject.ImplementedBy;
import models.Instance;
import models.repository.api.generic.ModelRepository;
import models.repository.impl.InstanceRepositoryJpa;

/**
 * Created by daniel seybold on 17.12.2014.
 */
@ImplementedBy(InstanceRepositoryJpa.class)
public interface InstanceRepository extends ModelRepository<Instance> {
}
