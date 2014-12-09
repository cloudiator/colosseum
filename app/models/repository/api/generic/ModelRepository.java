package models.repository.api.generic;

import models.generic.Model;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by daniel on 31.10.14.
 */
public interface ModelRepository<T extends Model> {

    @Nullable
    public T findById(Long id);

    //T create(T t);

    //public void flush();

    //T refresh(T t);

    public void delete(T t);

    //T update(T t);

    public void save(T t);

    public List<T> findAll();


}
