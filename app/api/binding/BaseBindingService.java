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
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.inject.Singleton;
import models.generic.Model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * A generic implementation of the ModelDtoConversionServiceWithRegistry.
 *
 * @author Daniel Baur
 */
@Singleton public class BaseBindingService implements BindingServiceWithRegistry {

    /**
     * Stores the registered converters.
     */
    private final Binders binders = new Binders();

    @Override public <T extends Model, S extends Dto> void addBinding(final Class<T> modelClass,
        final Class<S> dtoClass, final ModelToDtoBinding<T, S> binding) {
        checkNotNull(modelClass);
        checkNotNull(dtoClass);
        checkNotNull(binding);
        this.binders.add(modelClass, dtoClass, binding);
    }

    @Override
    public <T extends Model, S extends Dto> void addBinding(ModelToDtoBinding<T, S> binding) {
        checkNotNull(binding);
        try {
            Type t = binding.getClass().getGenericSuperclass();
            ParameterizedType pt = (ParameterizedType) t;

            //noinspection unchecked
            final Class<T> modelClass = (Class) pt.getActualTypeArguments()[0];
            //noinspection unchecked
            final Class<S> dtoClass = (Class) pt.getActualTypeArguments()[1];

            this.addBinding(modelClass, dtoClass, binding);
        } catch (Throwable t) {
            throw new IllegalArgumentException(
                "Could not resolve generic type of modelDtoConverter", t);
        }

    }

    @Override
    public <T extends Model, S extends Dto> S toDto(final T model, final Class<S> dtoClass) {
        checkNotNull(model);
        checkNotNull(dtoClass);
        //noinspection unchecked
        return (S) this.binders.get(model.getClass(), dtoClass).toDto(model);
    }

    @Override public <T extends Model, S extends Dto> S toDto(T model, S dto) {
        //noinspection unchecked
        return (S) this.binders.get(model.getClass(), dto.getClass()).toDto(model);
    }

    @Override public <T extends Model, S extends Dto> T toModel(S dto, Class<T> modelClass) {
        //noinspection unchecked
        return (T) this.binders.get(modelClass, dto.getClass()).toModel(dto);
    }

    @Override public <T extends Model, S extends Dto> T toModel(S dto, T model) {
        //noinspection unchecked
        return (T) this.binders.get(model.getClass(), dto.getClass()).toModel(dto, model);
    }

    /**
     * A storage for the registered binders.
     */
    static final class Binders {

        /**
         * A table storing the different binders.
         */
        private final Table<Class<? extends Model>, Class<? extends Dto>, ModelToDtoBinding>
            bindingTable = HashBasedTable.create();

        /**
         * Returns a stored binding.
         * <p>
         * Always returns a binding. If no binding is found
         * an {@link IllegalStateException} is thrown.
         *
         * @param modelClass the model class for the converter.
         * @param dtoClass   the dto class for the converter.
         * @return the converter.
         * @throws IllegalStateException if no binding was found
         */
        public ModelToDtoBinding get(final Class<? extends Model> modelClass,
            final Class<? extends Dto> dtoClass) {

            ModelToDtoBinding binding = this.bindingTable.get(modelClass, dtoClass);

            if (binding == null) {
                if (dtoClass.isAnnotationPresent(JsonSubTypes.class)) {
                    final JsonSubTypes.Type[] values =
                        dtoClass.getAnnotation(JsonSubTypes.class).value();
                    for (JsonSubTypes.Type value : values) {
                        binding = this.bindingTable.get(modelClass, value.value());
                        if (binding != null) {
                            break;
                        }
                    }
                }
            }

            checkState(binding != null, String
                .format("Could not retrieve converter for model: %s and dto: %s",
                    modelClass.getName(), dtoClass.getName()));

            return binding;
        }

        /**
         * Adds a new binding to the binding storage.
         * <p>
         * Note: adding a new binding for a tuple of modelClass and
         * dtoClass that already exists, will overwrite the currently
         * stored binding.
         *
         * @param modelClass the model class.
         * @param dtoClass   the dto class.
         * @param binding    the binding which should added.
         */
        public void add(final Class<? extends Model> modelClass,
            final Class<? extends Dto> dtoClass, final ModelToDtoBinding binding) {
            checkNotNull(modelClass);
            checkNotNull(dtoClass);
            checkNotNull(binding);
            this.bindingTable.put(modelClass, dtoClass, binding);
        }

    }

}
