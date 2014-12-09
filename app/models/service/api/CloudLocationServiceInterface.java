package models.service.api;

import com.google.inject.ImplementedBy;
import models.CloudLocation;
import models.service.api.generic.ModelServiceInterface;
import models.service.impl.CloudLocationService;

/**
 * Created by bwpc on 09.12.2014.
 */
@ImplementedBy(CloudLocationService.class)
public interface CloudLocationServiceInterface extends ModelServiceInterface<CloudLocation> {
}
