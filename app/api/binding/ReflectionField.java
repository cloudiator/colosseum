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

import javax.annotation.Nullable;
import java.lang.reflect.Field;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 20.03.15.
 */
public class ReflectionField<T> implements Getter<T>, Setter<T> {

    private final String fieldName;
    private final Class<T> fieldType;

    public static <S> ReflectionField<S> of(String fieldName, Class<S> fieldType) {
        return new ReflectionField<>(fieldName, fieldType);
    }

    public static ReflectionField<Object> of(Field field) {
        //noinspection unchecked
        return new ReflectionField<>(field.getName(), (Class<Object>) field.getType());
    }

    private ReflectionField(String fieldName, Class<T> fieldType) {
        checkNotNull(fieldName);
        checkArgument(!fieldName.isEmpty());
        checkNotNull(fieldType);

        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    @Nullable private Field getField(Class clazz, String fieldToFind) {

        Class currentClass = clazz;

        //todo refactor to use field finder
        while (currentClass != null) {
            for (Field field : currentClass.getDeclaredFields()) {
                if (field.getName().equals(fieldToFind)) {
                    return field;
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        return null;
    }

    private boolean isValidField(Field field, Class<?> fieldType) {
        return field != null && fieldType.isAssignableFrom(field.getType());
    }


    @Override public T getValue(Object object) {
        Field getField = getField(object.getClass(), fieldName);
        checkNotNull(getField, String
            .format("Could not find field %s on class of o (%s)", fieldName, object.getClass()));
        getField.setAccessible(true);
        checkArgument(isValidField(getField, fieldType), String
            .format("Illegal field type %s for field %s. Not assignable from %s",
                fieldType.getName(), fieldName, getField.getType()));

        try {
            //noinspection unchecked
            return (T) getField.get(object);
        } catch (IllegalAccessException e) {
            throw new BindingException(String
                .format("Could not get value on field %s on object %s having class %s.",
                    getField.getName(), object, object.getClass()), e);
        } catch (ClassCastException e) {
            throw new BindingException("Could not cast field value.", e);
        }
    }

    @Override public void setValue(Object object, T value) {
        Field getField = getField(object.getClass(), fieldName);
        checkNotNull(getField, String
            .format("Could not find field %s on class of o (%s)", fieldName, object.getClass()));
        getField.setAccessible(true);
        checkArgument(isValidField(getField, fieldType), String
            .format("Illegal field type %s for field %s. Not assignable from %s",
                fieldType.getName(), fieldName, getField.getType().getClass().getName()));

        try {
            getField.set(object, value);
        } catch (IllegalAccessException e) {
            throw new BindingException(String
                .format("Could not set value on field %s on object %s having class %s",
                    getField.getName(), object, object.getClass()), e);
        }
    }
}
