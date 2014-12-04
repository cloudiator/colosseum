package models.service.impl;

import com.google.inject.Inject;
import models.Cloud;
import models.CloudImage;
import models.Image;
import models.repository.api.CloudImageRepository;
import models.service.api.CloudImageServiceInterface;
import models.service.impl.generic.ModelService;

/**
 * Created by daniel on 03.11.14.
 */
public class CloudImageService extends ModelService<CloudImage> implements CloudImageServiceInterface {

    @Inject
    public CloudImageService(CloudImageRepository cloudImageRepository) {
        super(cloudImageRepository);
    }

    @Override
    public CloudImage getByCloudAndImage(final Cloud cloud, final Image image) {
        return ((CloudImageRepository) this.modelRepository).findByCloudAndImage(cloud, image);
    }
}
