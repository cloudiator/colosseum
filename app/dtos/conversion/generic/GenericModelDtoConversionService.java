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

package dtos.conversion.generic;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.inject.Singleton;
import dtos.conversion.api.DtoConverter;
import dtos.conversion.api.ModelDtoConversionServiceWithRegistry;
import dtos.generic.api.Dto;
import models.generic.Model;
import play.Logger;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A generic implementation of the ModelDtoConversionServiceWithRegistry.
 *
 * @author Daniel Baur
 */
@Singleton public class GenericModelDtoConversionService
    implements ModelDtoConversionServiceWithRegistry {

    /**
     * Stores the registered converters.
     */
    private final Converters converters = new Converters();

    @Override public <T extends Model, S extends Dto> void addConverter(final Class<T> modelClass,
        final Class<S> dtoClass, final DtoConverter<T, S> dtoConverter) {
        Logger.debug(String
            .format("Registered new converter for model: %s and dto %s: %s", modelClass.getName(),
                dtoClass.getName(), dtoConverter.getClass().getName()));
        checkNotNull(modelClass);
        checkNotNull(dtoClass);
        checkNotNull(dtoConverter);
        this.converters.add(modelClass, dtoClass, dtoConverter);
    }

    @Override
    public <T extends Model, S extends Dto> void addConverter(DtoConverter<T, S> dtoConverter) {
        checkNotNull(dtoConverter);
        try {
            Type t = dtoConverter.getClass().getGenericSuperclass();
            ParameterizedType pt = (ParameterizedType) t;

            //noinspection unchecked
            final Class<T> modelClass = (Class) pt.getActualTypeArguments()[0];
            //noinspection unchecked
            final Class<S> dtoClass = (Class) pt.getActualTypeArguments()[1];

            this.addConverter(modelClass, dtoClass, dtoConverter);
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
        return (S) this.converters.get(model.getClass(), dtoClass).toDto(model);
    }

    @Override public <T extends Model, S extends Dto> S toDto(T model, S dto) {
        //noinspection unchecked
        return (S) this.converters.get(model.getClass(), dto.getClass()).toDto(model);
    }

    @Override public <T extends Model, S extends Dto> T toModel(S dto, Class<T> modelClass) {
        //noinspection unchecked
        return (T) this.converters.get(modelClass, dto.getClass()).toModel(dto);
    }

    @Override public <T extends Model, S extends Dto> T toModel(S dto, T model) {
        //noinspection unchecked
        return (T) this.converters.get(model.getClass(), dto.getClass()).toModel(dto);
    }

    @Override public <T extends Model, S extends Dto> List<S> toDtos(final List<T> models,
        final Class<S> dtoClass) {
        checkNotNull(models);
        checkNotNull(dtoClass);
        List<S> dtos = new ArrayList<>(models.size());
        for (T model : models) {
            dtos.add(this.toDto(model, dtoClass));
        }
        return dtos;
    }

    /**
     * A storage for the registered converters.
     */
    final static class Converters {

        /**
         * A table storing the different converters.
         */
        private final Table<Class<? extends Model>, Class<? extends Dto>, DtoConverter>
            converterTable = HashBasedTable.create();

        /**
         * Returns a stored converter.
         * <p>
         * Always returns a converter. If no converter is found
         * a Null-pointer exception is thrown.
         *
         * @param modelClass the model class for the converter.
         * @param dtoClass   the dto class for the converter.
         * @return the converter.
         */
        public DtoConverter get(final Class<? extends Model> modelClass,
            final Class<? extends Dto> dtoClass) {
            return checkNotNull(this.converterTable.get(modelClass, dtoClass), String
                .format("Could not retrieve converter for model: %s and dto: %s",
                    modelClass.getName(), dtoClass.getName()));
        }

        /**
         * Adds a new converter to the converter storage.
         * <p>
         * Note: adding a new converter for a tuple of modelClass and
         * dtoClass that already exists, will overwrite the currently
         * stored converter.
         *
         * @param modelClass   the model class.
         * @param dtoClass     the dto class.
         * @param dtoConverter the converter which should added.
         */
        public void add(final Class<? extends Model> modelClass,
            final Class<? extends Dto> dtoClass, final DtoConverter dtoConverter) {
            checkNotNull(modelClass);
            checkNotNull(dtoClass);
            checkNotNull(dtoConverter);
            this.converterTable.put(modelClass, dtoClass, dtoConverter);
        }

    }

}
