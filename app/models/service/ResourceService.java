package models.service;

import models.generic.Resource;

import javax.annotation.Nullable;

/**
 * Created by daniel on 08.05.15.
 */
public interface ResourceService<T extends Resource> {

    @Nullable T getByUuid(String uuid);

}
