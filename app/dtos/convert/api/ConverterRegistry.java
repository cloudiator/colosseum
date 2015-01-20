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

package dtos.convert.api;

import com.google.inject.ImplementedBy;
import dtos.convert.converters.api.ModelDtoConverter;
import dtos.convert.impl.DefaultModelDtoConversionService;
import dtos.generic.api.Dto;
import models.generic.Model;

/**
 * Interface for the Converter Registry.
 *
 * Provides methods for adding converters to the
 * registry.
 *
 * @author Daniel Baur
 */
@ImplementedBy(DefaultModelDtoConversionService.class)
public interface ConverterRegistry {

    /**
     * Adds a new converter.
     *
     * Adding a converter for a modelClass and a dtoClass that already
     * have been registered, will overwrite the exisiting converter.
     *
     * @param modelClass the class of the model
     * @param dtoClass the class of the dto.
     * @param modelDtoConverter a converter for the given model and dto.
     * @param <T> type of the model
     * @param <S> type of the dto.
     */
    public <T extends Model, S extends Dto> void addConverter(Class<T> modelClass, Class<S> dtoClass, ModelDtoConverter<T, S> modelDtoConverter);

    /**
     * Adds a new converter without the need to specify
     * the classes it converts.
     *
     * Adding a converter for a modelClass and a dtoClass that already
     * have been registered, will overwrite the exisiting converter.
     *
     * @param modelDtoConverter a for model and dto.
     * @param <T> type of the model.
     * @param <S> type of the dto.
     */
    public <T extends Model, S extends Dto> void addConverter(ModelDtoConverter<T, S> modelDtoConverter);
}
