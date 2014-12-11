package models.service.impl;

import com.google.inject.Inject;
import models.CloudApi;
import models.repository.api.CloudApiRepository;
import models.service.api.CloudApiServiceInterface;
import models.service.impl.generic.ModelService;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class CloudApiService extends ModelService<CloudApi> implements CloudApiServiceInterface {

    @Inject
    public CloudApiService(CloudApiRepository cloudApiRepository){super(cloudApiRepository);}
}
