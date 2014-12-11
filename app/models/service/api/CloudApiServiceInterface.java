package models.service.api;

import com.google.inject.ImplementedBy;
import models.CloudApi;
import models.service.api.generic.ModelServiceInterface;
import models.service.impl.CloudApiService;

/**
 * Created by daniel seybold on 11.12.2014.
 */
@ImplementedBy(CloudApiService.class)
public interface CloudApiServiceInterface extends ModelServiceInterface<CloudApi> {
}
