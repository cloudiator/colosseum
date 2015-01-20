/*
 * Copyright (c) 2015 University of Ulm
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
