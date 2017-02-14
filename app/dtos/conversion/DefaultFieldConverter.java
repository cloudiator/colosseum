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

package dtos.conversion;

import de.uniulm.omi.cloudiator.common.FieldFinder;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Collectors;

import dtos.api.Dto;
import de.uniulm.omi.cloudiator.persistance.entities.Model;

/**
 * Created by daniel on 14.04.15.
 */
public class DefaultFieldConverter<T extends Model, S extends Dto> implements DtoConverter<T, S> {

    private Class<T> tClass;
    private Class<S> sClass;

    public DefaultFieldConverter(Class<T> tClass, Class<S> sClass) {
        this.tClass = tClass;
        this.sClass = sClass;
    }

    private Iterable<Field> getFieldsToBind() {

        Set<Field> dtoFields = FieldFinder.of(sClass).getFields();
        Set<Field> modelFields = FieldFinder.of(tClass).getFields();

        Set<String> dtoFieldNames =
            dtoFields.stream().map(Field::getName).collect(Collectors.toSet());
        Set<String> modelFieldNames =
            modelFields.stream().map(Field::getName).collect(Collectors.toSet());

        dtoFieldNames.retainAll(modelFieldNames);

        return dtoFields.stream().filter(field -> dtoFieldNames.contains(field.getName()))
            .collect(Collectors.toList());

    }

    private S bindFromModelToDto(T model, S dto) {
        for (Field field : getFieldsToBind()) {
            ReflectionField<Object> reflectionFieldModel = ReflectionField.of(field);
            ReflectionField<Object> reflectionFieldDto = ReflectionField.of(field);
            reflectionFieldDto.setValue(dto, reflectionFieldModel.getValue(model));
        }
        return dto;
    }

    private T bindFromDtoToModel(S dto, T model) {
        for (Field field : getFieldsToBind()) {
            ReflectionField<Object> reflectionFieldModel = ReflectionField.of(field);
            ReflectionField<Object> reflectionFieldDto = ReflectionField.of(field);
            reflectionFieldModel.setValue(model, reflectionFieldDto.getValue(dto));
        }
        return model;
    }

    @Override public final T toModel(S dto) {
        return bindFromDtoToModel(dto, new TypeBuilder<T>().getInstance(tClass));
    }

    @Override public final T toModel(S dto, T model) {
        return bindFromDtoToModel(dto, model);
    }

    @Override public final S toDto(T model) {
        return bindFromModelToDto(model, new TypeBuilder<S>().getInstance(sClass));
    }

    @Override public final S toDto(T model, S dto) {
        return bindFromModelToDto(model, dto);
    }

}
