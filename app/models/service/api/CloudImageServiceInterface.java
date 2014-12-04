package models.service.api;

import com.google.inject.ImplementedBy;
import models.*;
import models.service.api.generic.ModelServiceInterface;
import models.service.impl.CloudImageService;

/**
 * Created by daniel on 03.11.14.
 */
@ImplementedBy(CloudImageService.class)
public interface CloudImageServiceInterface extends ModelServiceInterface<CloudImage>{

    public CloudImage getByCloudAndImage(Cloud cloud, Image image);


}
