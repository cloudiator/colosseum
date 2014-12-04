package models.service.api.generic;

import models.generic.NamedModel;

/**
 * Created by daniel on 31.10.14.
 */
public interface NamedModelServiceInterface<T extends NamedModel> extends ModelServiceInterface<T> {

    public T getByName(String name);

}
