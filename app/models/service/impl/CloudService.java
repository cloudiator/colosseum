package models.service.impl;

import com.google.inject.Inject;
import models.Cloud;
import models.repository.api.CloudRepository;
import models.service.api.CloudServiceInterface;
import models.service.impl.generic.NamedModelService;

/**
 * Created by daniel on 31.10.14.
 */
public class CloudService extends NamedModelService<Cloud> implements CloudServiceInterface  {

    @Inject
    public CloudService(CloudRepository cloudRepository) {
        super(cloudRepository);
    }
}
