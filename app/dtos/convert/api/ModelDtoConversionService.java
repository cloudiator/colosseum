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

package dtos.convert.api;

import com.google.inject.ImplementedBy;
import dtos.convert.impl.DefaultModelDtoConversionService;
import dtos.generic.api.Dto;
import dtos.generic.impl.Link;
import models.generic.Model;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface for the Model to DTO Conversion service.
 * <p>
 * Defines methods for converting model object to dto objects
 * and vice versa.
 * <p>
 * If no converter is registered, an exception is thrown.
 */
@ImplementedBy(DefaultModelDtoConversionService.class)
public interface ModelDtoConversionService {

    /**
     * Converts the given model to the expected dto class.
     *
     * @param model    the model which will be converted.
     * @param dtoClass the target dto class.
     * @param <T>      type of the model.
     * @param <S>      type of the dto.
     * @return the resulting dto of the conversion.
     */
    public <T extends Model, S extends Dto> S toDto(T model, Class<S> dtoClass);

    /**
     * Converts the given model to the expected dto class, and sets the given links.
     *
     * @param model    the model which will be converted
     * @param dtoClass the target dto class.
     * @param links    a set of links attached to the dto.
     * @param <T>      type of the model.
     * @param <S>      type of the dto.
     * @return the resulting dto of the conversion.
     */
    public <T extends Model, S extends Dto> S toDto(T model, Class<S> dtoClass, Set<Link> links);

    /**
     * Converts the given dto to the model, by using the given model as
     * template. Only sets the fields provided by the dto.
     *
     * @param dto        the dto which should be merged into the model.
     * @param model      the model in which the dto is merged.
     * @param modelClass the expected model class.
     * @param <T>        type of the model.
     * @param <S>        type of the dto.
     * @return the resulting model object.
     */
    public <T extends Model, S extends Dto> T toModel(S dto, T model, Class<T> modelClass);

    /**
     * Helper for converting a list of models to a list of dtos.
     *
     * @param models   a list of models.
     * @param dtoClass the expected dto class.
     * @param <T>      type of the model.
     * @param <S>      type of the dto.
     * @return a list of resulting dto objects.
     */
    public <T extends Model, S extends Dto> List<S> toDtos(List<T> models, Class<S> dtoClass);

    /**
     * Helper for converting a list of models to a list of dtos.
     *
     * @param <T>      type of the model.
     * @param <S>      type of the dto.
     * @param models   a list of models.
     * @param dtoClass the expected dto class.
     * @param links    a map containing the links for the models.
     *                 Key: id of the model, Value: Links for this model
     * @return a list of resulting dto objects.
     */
    public <T extends Model, S extends Dto> List<S> toDtos(List<T> models, Class<S> dtoClass, Map<Long, Set<Link>> links);
}
