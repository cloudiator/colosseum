package models.service;

import models.generic.Resource;

import javax.annotation.Nullable;

/**
 * Created by daniel on 08.05.15.
 */
public interface ResourceRepository<T extends Resource> {
    @Nullable T findByUuid(String uuid);
}
