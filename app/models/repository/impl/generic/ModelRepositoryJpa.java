package models.repository.impl.generic;

import models.generic.Model;
import play.db.jpa.JPA;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static com.google.inject.internal.util.$Preconditions.checkArgument;
import static com.google.inject.internal.util.$Preconditions.checkNotNull;

/**
 * Created by daniel on 31.10.14.
 */
public abstract class ModelRepositoryJpa<T extends Model> implements models.repository.api.generic.ModelRepository<T> {

    public static EntityManager em() {
        return JPA.em();
    }

    protected final Class<T> type;

    public ModelRepositoryJpa() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        //noinspection unchecked
        this.type = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public T findById(Long id) {
        checkNotNull(id);
        checkArgument(id > 0, "id must be > 0");
        return em().find(type, id);
    }

    protected void persist(final T t) {
        em().persist(t);
    }

    @Override
    public void save(final T t) {
        checkNotNull(t);
        if(t.getId() == null) {
            this.persist(t);
        } else {
            this.update(t);
        }
        this.flush();
        this.refresh(t);
    }

    protected T update(final T t) {
        return em().merge(t);
    }

    protected void flush() {
        em().flush();
    }

    protected T refresh(final T t)
    {
        em().refresh(t);
        return t;
    }

    @Override
    public void delete(final T t) {
        checkNotNull(t);
        em().remove(t);
    }

    @Override
    public List<T> findAll() {
        String queryString = String.format("from %s", type.getName());
        Query query = em().createQuery(queryString);
        //noinspection unchecked
        return query.getResultList();
    }
}
