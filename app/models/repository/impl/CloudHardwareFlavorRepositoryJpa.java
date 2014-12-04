package models.repository.impl;

import models.Cloud;
import models.CloudHardware;
import models.Hardware;
import models.repository.api.CloudHardwareFlavorRepository;
import models.repository.impl.generic.ModelRepositoryJpa;

import static models.util.JpaResultHelper.*;

/**
 * Created by daniel on 31.10.14.
 */
public class CloudHardwareFlavorRepositoryJpa extends ModelRepositoryJpa<CloudHardware> implements CloudHardwareFlavorRepository {

    /**
     * Searches the concrete hardware flavor based on the given cloud and the hardware
     *
     * @param cloud          the cloud
     * @param hardware the hardware flavor
     * @return the unique cloud hardware if any, otherwise null.
     */
    @Override
    public CloudHardware findByCloudAndHardwareFlavor(final Cloud cloud, final Hardware hardware) {

        return (CloudHardware) getSingleResultOrNull(em()
                .createQuery("from CloudHardwareFlavor ch where cloud = :cloud and hardwareFlavor = :hardwareFlavor")
                .setParameter("cloud", cloud)
                .setParameter("hardwareFlavor", hardware));

    }
}
