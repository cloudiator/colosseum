package models.repository.impl.generic;

import models.generic.NamedModel;

import javax.persistence.Query;

import static models.util.JpaResultHelper.*;

/**
 * Created by daniel on 31.10.14.
 */
public class NamedModelRepositoryJpa<T extends NamedModel> extends ModelRepositoryJpa<T> implements models.repository.api.generic.NamedModelRepository<T> {

    @Override
    public T findByName(String name) {
        String queryString = String.format("from %s where name = :name", type.getName());
        Query query = em().createQuery(queryString).setParameter("name",name);
        //noinspection unchecked
        return (T) getSingleResultOrNull(query);
    }
}
