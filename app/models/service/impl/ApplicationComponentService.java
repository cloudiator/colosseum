package models.service.impl;

import com.google.inject.Inject;
import models.ApplicationComponent;
import models.repository.api.ApplicationComponentRepository;
import models.service.api.ApplicationComponentServiceInterface;
import models.service.impl.generic.ModelService;

/**
 * Created by daniel seybold on 17.12.2014.
 */
public class ApplicationComponentService extends ModelService<ApplicationComponent> implements ApplicationComponentServiceInterface {

    @Inject
    public ApplicationComponentService(ApplicationComponentRepository applicationComponentRepository){super(applicationComponentRepository);}
}
