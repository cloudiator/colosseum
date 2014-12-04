package models.repository.api.generic;

import models.generic.NamedModel;

/**
 * Created by daniel on 31.10.14.
 */
public interface NamedModelRepository<T extends NamedModel> extends ModelRepository<T> {
    public T findByName(String name);
}
