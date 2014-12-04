package models.repository.impl;

import models.Cloud;
import models.CloudImage;
import models.Image;
import models.repository.api.CloudImageRepository;
import models.repository.impl.generic.ModelRepositoryJpa;

import static models.util.JpaResultHelper.*;

/**
 * Created by daniel on 31.10.14.
 */
public class CloudImageRepositoryJpa extends ModelRepositoryJpa<CloudImage> implements CloudImageRepository {

    /**
     * Searches the concrete image on the given cloud and the image
     *
     * @param cloud the cloud
     * @param image the image
     * @return the unique cloud image if any, otherwise null.
     */
    @Override
    public CloudImage findByCloudAndImage(final Cloud cloud, final Image image) {

        return (CloudImage) getSingleResultOrNull(em()
                .createQuery("from Image i where cloud = :cloud and image = :image")
                .setParameter("cloud", cloud)
                .setParameter("image", image));

    }
}
