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

import com.google.inject.Inject;

import java.util.List;

import javax.annotation.Nullable;

import models.generic.Model;
import util.logging.Loggers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 31.10.14.
 */
public class BaseModelService<T extends Model> implements ModelService<T> {

    private static final play.Logger.ALogger LOGGER = Loggers.of(Loggers.DATABASE);

    protected final ModelRepository<T> modelRepository;

    @Inject public BaseModelService(ModelRepository<T> modelRepository) {
        checkNotNull(modelRepository);
        this.modelRepository = modelRepository;
    }

    @Override @Nullable public T getById(Long id) {
        return modelRepository.findById(id);
    }

    @Override public List<T> getByAttributeValue(String attribute, String value)
        throws IllegalSearchException {
        try {
            return modelRepository.findByColumn(attribute, value);
        } catch (IllegalColumnException e) {
            throw new IllegalSearchException(e);
        } catch (Exception e) {
            LOGGER.warn("Unexpected error during execution of search.", e);
            throw new IllegalSearchException(e);
        }
    }

    @Override @Nullable public T getByUuid(String uuid) {
        return modelRepository.findByUuid(uuid);
    }

    @Override public List<T> getAll() {
        return modelRepository.findAll();
    }

    @Override public void save(T entity) {
        this.modelRepository.save(entity);
    }

    @Override public void delete(T entity) {
        this.modelRepository.delete(entity);
    }
}
