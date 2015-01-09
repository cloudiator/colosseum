package models.repository.api;

import com.google.inject.ImplementedBy;
import models.CommunicationChannel;
import models.repository.api.generic.ModelRepository;
import models.repository.impl.CommunicationChannelRepositoryJpa;

/**
 * Created by daniel on 09.01.15.
 */
@ImplementedBy(CommunicationChannelRepositoryJpa.class)
public interface CommunicationChannelRepository extends ModelRepository<CommunicationChannel> {
}
