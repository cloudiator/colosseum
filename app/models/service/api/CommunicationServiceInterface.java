package models.service.api;

import com.google.inject.ImplementedBy;
import models.Communication;
import models.service.api.generic.ModelServiceInterface;
import models.service.impl.CommunicationService;

/**
 * Created by daniel on 09.01.15.
 */
@ImplementedBy(CommunicationService.class)
public interface CommunicationServiceInterface extends ModelServiceInterface<Communication> {
}
