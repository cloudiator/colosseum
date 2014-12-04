package models.service.api.generic;

import models.generic.Model;

import java.util.List;
import java.util.Map;

/**
 * Created by daniel on 31.10.14.
 */
public interface ModelServiceInterface<T extends Model> {

    public T getById(Long id);

    public List<T> getAll();

    public Map<String,String> getOptions();

    public void save(T t);

    public void delete(T t);

}
