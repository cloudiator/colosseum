package models.service.impl;

import com.google.inject.Inject;
import models.Component;
import models.repository.api.ComponentRepository;
import models.service.api.ComponentServiceInterface;
import models.service.impl.generic.NamedModelService;

/**
 * Created by daniel seybold on 16.12.2014.
 */
public class ComponentService extends NamedModelService<Component> implements ComponentServiceInterface {

    @Inject
    public ComponentService(ComponentRepository componentRepository){
        super(componentRepository);
    }
}
