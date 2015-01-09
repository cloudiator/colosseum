package models.service.impl;

import com.google.inject.Inject;
import models.CommunicationChannel;
import models.repository.api.CommunicationChannelRepository;
import models.service.api.CommunicationChannelServiceInterface;
import models.service.impl.generic.ModelService;

/**
 * Created by daniel on 09.01.15.
 */
public class CommunicationChannelService extends ModelService<CommunicationChannel> implements CommunicationChannelServiceInterface {

    @Inject
    public CommunicationChannelService(CommunicationChannelRepository communicationChannelRepository) {
        super(communicationChannelRepository);
    }
}
