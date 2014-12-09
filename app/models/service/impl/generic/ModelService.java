package models.service.impl.generic;

import models.generic.Model;
import models.repository.api.generic.ModelRepository;
import models.service.api.generic.ModelServiceInterface;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 31.10.14.
 */
public abstract class ModelService<T extends Model> implements ModelServiceInterface<T> {

    protected final ModelRepository<T> modelRepository;

    public ModelService(ModelRepository<T> modelRepository) {
        checkNotNull(modelRepository);
        this.modelRepository = modelRepository;
    }

    @Override
    @Nullable
    public T getById(Long id) {
        return modelRepository.findById(id);
    }

    @Override
    public List<T> getAll() {
        return modelRepository.findAll();
    }

    @Override
    public void save(T entity) {
        this.modelRepository.save(entity);
    }

    @Override
    public void delete(T entity) {
        this.modelRepository.delete(entity);
    }

    @Override
    public Map<String, String> getOptions() {
        LinkedHashMap<String, String> options = new LinkedHashMap<>();
        for (T t : this.getAll()) {
            options.put(t.getId().toString(), t.toString());
        }
        return options;
    }
}
