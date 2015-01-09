package models.repository.api;

import com.google.inject.ImplementedBy;
import models.Communication;
import models.repository.api.generic.ModelRepository;
import models.repository.impl.CommunicationRepositoryJpa;

/**
 * Created by daniel on 09.01.15.
 */
@ImplementedBy(CommunicationRepositoryJpa.class)
public interface CommunicationRepository extends ModelRepository<Communication> {
}
