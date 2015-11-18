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

package models.service;

import com.github.drapostolos.typeparser.TypeParser;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import de.uniulm.omi.cloudiator.common.FieldFinder;
import models.generic.Model;
import play.db.jpa.JPA;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 31.10.14.
 */
class BaseModelRepositoryJpa<T extends Model> implements ModelRepository<T> {

    protected final Class<T> type;

    @Inject BaseModelRepositoryJpa(TypeLiteral<T> type) {
        //noinspection unchecked
        this.type = (Class<T>) type.getRawType();
    }

    protected EntityManager em() {
        return JPA.em();
    }

    @Override @Nullable public T findById(Long id) {
        checkNotNull(id);
        return em().find(type, id);
    }

    @Override public List<T> findByColumn(String columnName, String value)
        throws IllegalColumnException {
        CriteriaBuilder criteriaBuilder = em().getCriteriaBuilder();
        CriteriaQuery<T> criteria = criteriaBuilder.createQuery(type);
        Root<T> tRoot = criteria.from(type);
        criteria.select(tRoot);
        TypeParser typeParser = TypeParser.newBuilder().build();
        Optional<Field> field = FieldFinder.of(type).getField(columnName);

        if (!field.isPresent()) {
            throw new IllegalColumnException(
                String.format("Type %s does not hold column %s", type, columnName));
        }

        Object o = typeParser.parseType(value, field.get().getType());
        criteria.where(criteriaBuilder.equal(tRoot.get(columnName), o));

        return em().createQuery(criteria).getResultList();
    }

    protected void persist(final T t) {
        em().persist(t);
    }

    @Override public void save(final T t) {
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

    @Override public void delete(final T t) {
        checkNotNull(t);
        em().remove(t);
    }



    @Override public List<T> findAll() {
        String queryString = String.format("from %s", type.getName());
        Query query = em().createQuery(queryString);
        //noinspection unchecked
        return query.getResultList();
    }

    @Nullable @Override public T findByUuid(String uuid) {
        checkNotNull(uuid);
        String queryString = String.format("from %s where uuid=:uuid", type.getName());
        Query query = em().createQuery(queryString).setParameter("uuid", uuid);
        try {
            //noinspection unchecked
            return (T) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
