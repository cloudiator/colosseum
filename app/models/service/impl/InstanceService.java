package models.service.impl;

import com.google.inject.Inject;
import models.Instance;
import models.repository.api.InstanceRepository;
import models.service.api.InstanceServiceInterface;
import models.service.impl.generic.ModelService;

/**
 * Created by daniel seybold on 17.12.2014.
 */
public class InstanceService extends ModelService<Instance> implements InstanceServiceInterface {

    @Inject
    public InstanceService(InstanceRepository instanceRepository){
        super(instanceRepository);
    }
}
