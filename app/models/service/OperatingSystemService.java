package models.service;

import models.OperatingSystem;

/**
 * Created by daniel on 23.07.15.
 */
public interface OperatingSystemService extends ModelService<OperatingSystem> {

    OperatingSystem findByImageName(String imageName);
}
