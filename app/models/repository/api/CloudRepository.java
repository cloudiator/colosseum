package models.repository.api;

import com.google.inject.ImplementedBy;
import models.Cloud;
import models.repository.api.generic.NamedModelRepository;
import models.repository.impl.CloudRepositoryJpa;

/**
 * Created by daniel on 31.10.14.
 */
@ImplementedBy(CloudRepositoryJpa.class)
public interface CloudRepository extends NamedModelRepository<Cloud> {

}
