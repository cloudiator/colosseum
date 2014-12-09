package models.service.impl;

import com.google.inject.Inject;
import models.CloudLocation;
import models.repository.api.CloudLocationRepository;
import models.service.api.CloudLocationServiceInterface;
import models.service.impl.generic.ModelService;

/**
 * Created by bwpc on 09.12.2014.
 */
public class CloudLocationService extends ModelService<CloudLocation> implements CloudLocationServiceInterface {

    @Inject
    public CloudLocationService(CloudLocationRepository cloudLocationRepository) {
        super(cloudLocationRepository);
    }
}
