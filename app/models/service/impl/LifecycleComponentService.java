package models.service.impl;

import com.google.inject.Inject;
import models.LifecycleComponent;
import models.repository.api.LifecycleComponentRepository;
import models.service.api.LifecycleComponentServiceInterface;
import models.service.impl.generic.NamedModelService;

/**
 * Created by daniel seybold on 16.12.2014.
 */
public class LifecycleComponentService extends NamedModelService<LifecycleComponent> implements LifecycleComponentServiceInterface {

    @Inject
    public LifecycleComponentService(LifecycleComponentRepository lifecycleComponentRepository){super(lifecycleComponentRepository);}
}
