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

import dtos.conversion.api.BindingException;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 20.03.15.
 */
public class ReflectionField<U> {

    private final Field field;
    private final Object o;

    private ReflectionField(String fieldName, Class<U> fieldType, Object o) {
        checkNotNull(fieldName);
        checkArgument(!fieldName.isEmpty());
        checkNotNull(o);

        Field getField = getField(o.getClass(), fieldName);
        checkNotNull(getField,
            String.format("Could not find field %s on class of o (%s)", fieldName, o.getClass()));

        checkArgument(isValidField(getField, fieldType), String
            .format("Illegal field type %s for field %s. Not assignable from %s",
                fieldType.getName(), fieldName, getField.getType().getClass().getName()));


        getField.setAccessible(true);
        this.field = getField;
        this.o = o;
    }

    public static <P> ReflectionField<P> of(String fieldName, Class<P> fieldType, Object o) {
        return new ReflectionField<>(fieldName, fieldType, o);
    }

    public static ReflectionField<Object> of(String fieldName, Object o) {
        return new ReflectionField<>(fieldName, Object.class, o);
    }

    public U getValue() {
        try {
            //noinspection unchecked
            return (U) field.get(o);
        } catch (IllegalAccessException e) {
            throw new BindingException(String
                .format("Could not get value on field %s on object %s having class %s.",
                    field.getName(), o, o.getClass()), e);
        } catch (ClassCastException e) {
            throw new BindingException("Could not cast field value.", e);
        }
    }

    public void setValue(U value) {
        try {
            field.set(o, value);
        } catch (IllegalAccessException e) {
            throw new BindingException(String
                .format("Could not set value on field %s on object %s having class %s",
                    field.getName(), o, o.getClass()), e);
        }
    }

    @Nullable private Field getField(Class clazz, String fieldToFind) {
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.getName().equals(fieldToFind)) {
                    return field;
                }
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    private boolean isValidField(Field field, Class<?> fieldType) {
        return field != null && fieldType.isAssignableFrom(field.getType());
    }


}
