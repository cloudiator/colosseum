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

package api.binding;

import api.dto.Dto;
import com.google.inject.ImplementedBy;
import models.generic.Model;

/**
 * Interface for the binding Registry.
 * <p>
 * Provides methods for adding bindings to the
 * registry.
 *
 * @author Daniel Baur
 */
@ImplementedBy(DefaultBindingService.class) public interface BindingRegistry {

    /**
     * Adds a new binding.
     * <p>
     * Adding a binding for a modelClass and a dtoClass that already
     * have been registered, will overwrite the existing binding.
     *
     * @param modelClass the class of the model
     * @param dtoClass   the class of the dto.
     * @param binding    a converter for the given model and dto.
     * @param <T>        type of the model
     * @param <S>        type of the dto.
     */
    <T extends Model, S extends Dto> void addBinding(Class<T> modelClass, Class<S> dtoClass,
        ModelToDtoBinding<T, S> binding);

    /**
     * Adds a new binding without the need to specify
     * the classes it binds.
     * <p>
     * Adding a binding for a modelClass and a dtoClass that already
     * have been registered, will overwrite the existing converter.
     *
     * @param binding a for model and dto.
     * @param <T>     type of the model.
     * @param <S>     type of the dto.
     */
    <T extends Model, S extends Dto> void addBinding(ModelToDtoBinding<T, S> binding);
}
