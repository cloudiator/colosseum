package models.service.impl.generic;

import models.repository.api.generic.NamedModelRepository;
import models.service.api.generic.NamedModelServiceInterface;
import models.generic.NamedModel;

/**
 * Created by daniel on 31.10.14.
 */
public abstract class NamedModelService<T extends NamedModel> extends ModelService<T> implements NamedModelServiceInterface<T> {

    protected NamedModelRepository<T> modelRepository;

    public NamedModelService(NamedModelRepository<T> namedModelRepository) {
        super(namedModelRepository);
    }

    @Override
    public T getByName(String name) {
        return modelRepository.findByName(name);
    }
}
