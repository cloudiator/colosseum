/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package models.repository.impl.generic;

import models.generic.Model;
import play.db.jpa.JPA;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

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
        return em().find(type, id);
    }

    protected void persist(final T t) {
        em().persist(t);
    }

    @Override
    public void save(final T t) {
        checkNotNull(t);
        if (t.getId() == null) {
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

    protected T refresh(final T t) {
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
