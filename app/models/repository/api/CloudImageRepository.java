package models.repository.api;

import com.google.inject.ImplementedBy;
import models.Cloud;
import models.CloudImage;
import models.Image;
import models.repository.api.generic.ModelRepository;
import models.repository.impl.CloudImageRepositoryJpa;

/**
 * Created by daniel on 31.10.14.
 */
@ImplementedBy(CloudImageRepositoryJpa.class)
public interface CloudImageRepository extends ModelRepository<CloudImage> {

    public CloudImage findByCloudAndImage(Cloud cloud, Image image);

}
