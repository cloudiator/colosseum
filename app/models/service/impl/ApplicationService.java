package models.service.impl;

import com.google.inject.Inject;
import models.Application;
import models.repository.api.ApplicationRepository;
import models.service.api.ApplicationServiceInterface;
import models.service.impl.generic.NamedModelService;

/**
 * Created by daniel seybold on 16.12.2014.
 */
public class  ApplicationService extends NamedModelService<Application> implements ApplicationServiceInterface {

    @Inject
    public ApplicationService(ApplicationRepository applicationRepository){super (applicationRepository);}
}
