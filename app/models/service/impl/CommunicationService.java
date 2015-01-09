package models.service.impl;

import com.google.inject.Inject;
import models.Communication;
import models.repository.api.CommunicationRepository;
import models.service.api.CommunicationServiceInterface;
import models.service.impl.generic.ModelService;

/**
 * Created by daniel on 09.01.15.
 */
public class CommunicationService extends ModelService<Communication> implements CommunicationServiceInterface {

    @Inject
    public CommunicationService(CommunicationRepository communicationRepository) {
        super(communicationRepository);
    }
}
