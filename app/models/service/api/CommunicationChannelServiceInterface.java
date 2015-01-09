package models.service.api;

import com.google.inject.ImplementedBy;
import models.CommunicationChannel;
import models.service.api.generic.ModelServiceInterface;
import models.service.impl.CommunicationChannelService;

/**
 * Created by daniel on 09.01.15.
 */
@ImplementedBy(CommunicationChannelService.class)
public interface CommunicationChannelServiceInterface extends ModelServiceInterface<CommunicationChannel> {
}
