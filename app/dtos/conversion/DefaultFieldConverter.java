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

import dtos.api.Dto;
import models.generic.Model;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
        Set<Field> dtoFields = new FieldFinder(sClass).getFields();
        Set<Field> modelFields = new FieldFinder(tClass).getFields();
        dtoFields.retainAll(modelFields);
        return dtoFields;
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

    @Override public T toModel(S dto) {
        return bindFromDtoToModel(dto, new TypeBuilder<T>().getInstance(tClass));
    }

    @Override public T toModel(S dto, T model) {
        return bindFromDtoToModel(dto, model);
    }

    @Override public S toDto(T model) {
        return bindFromModelToDto(model, new TypeBuilder<S>().getInstance(sClass));
    }

    @Override public S toDto(T model, S dto) {
        return bindFromModelToDto(model, dto);
    }

    private static class FieldFinder {

        private final Class c;

        private FieldFinder(Class c) {
            this.c = c;
        }

        private Set<Field> getFields() {
            Class clazz = c;
            Set<Field> fields = new HashSet<>();
            while (clazz != null) {
                Collections.addAll(fields, clazz.getDeclaredFields());
                clazz = clazz.getSuperclass();
            }
            return fields;
        }

    }
}
