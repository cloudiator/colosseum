/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package dtos.conversion;

import com.google.common.reflect.Invokable;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 25.08.15.
 */
public class ReflectionMethods {

    public static <T> ReflectionSetMethod<T> getSetter(String methodName, Class<T> setType) {
        return new ReflectionSetMethod<>(methodName, setType);
    }

    public static <T> ReflectionGetMethod<T> getGetter(String methodName, Class<T> getType) {
        return new ReflectionGetMethod<>(methodName, getType);
    }

    @Nullable private static Method getMethod(Class clazz, String methodToFind) {
        while (clazz != null) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().equals(methodToFind)) {
                    return method;
                }
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    private static class ReflectionSetMethod<T> implements Setter<T> {

        private final String methodName;
        private final Class<T> setType;

        private ReflectionSetMethod(String methodName, Class<T> setType) {

            checkNotNull(methodName);
            checkArgument(!methodName.isEmpty());
            checkNotNull(setType);

            this.methodName = methodName;
            this.setType = setType;
        }

        private boolean isValidSetType(Invokable<?, Object> invokable, Class<?> setType) {
            return invokable != null && setType.isAssignableFrom(
                invokable.getParameters().iterator().next().getType().getRawType());
        }

        @Override public void setValue(Object object, T arg) {
            Method method = getMethod(object.getClass(), methodName);

            if (method == null) {
                throw new IllegalArgumentException(String
                    .format("Method %s does not exist in class %s", methodName, object.getClass()));
            }

            method.setAccessible(true);

            final Invokable<?, Object> invokable = Invokable.from(method);

            //check if accessible
            if (!invokable.isAccessible()) {
                throw new IllegalArgumentException(String
                    .format("Method %s is not accessible on class %s", method, object.getClass()));
            }

            //check if return type is correct
            if (!isValidSetType(invokable, setType)) {
                throw new IllegalArgumentException(String
                    .format("Method set type %s is not invokable from set type %s",
                        invokable.getParameters().iterator().next().getType(), setType));
            }

            //check that it does not required arguments
            if (invokable.getParameters().size() != 1) {
                throw new IllegalArgumentException(
                    "Set method should require exactly one argument arguments.");
            }

            try {
                //noinspection unchecked
                method.invoke(object, arg);
            } catch (IllegalAccessException e) {
                throw new BindingException(String
                    .format("Could not set value on method %s on object %s having class %s.",
                        method.getName(), object, object.getClass()), e);
            } catch (ClassCastException e) {
                throw new BindingException("Could not cast field value.", e);
            } catch (InvocationTargetException e) {
                throw new BindingException(String.format(
                    "Exception occurred while calling method %s on object %s having class %s",
                    method.getName(), object, object.getClass()), e);
            }
        }
    }


    private static class ReflectionGetMethod<T> implements Getter<T> {

        private final String methodName;
        private final Class<T> returnType;

        private ReflectionGetMethod(String methodName, Class<T> returnType) {

            checkNotNull(methodName);
            checkArgument(!methodName.isEmpty());
            checkNotNull(returnType);

            this.methodName = methodName;
            this.returnType = returnType;
        }

        private boolean isValidReturnType(Invokable<?, Object> invokable, Class<?> returnType) {
            return invokable != null && returnType
                .isAssignableFrom(invokable.getReturnType().getRawType());
        }


        @Override public T getValue(Object object) {

            Method method = getMethod(object.getClass(), methodName);


            if (method == null) {
                throw new IllegalArgumentException(String
                    .format("Method %s does not exist in class %s", methodName, object.getClass()));
            }

            method.setAccessible(true);
            final Invokable<?, Object> invokable = Invokable.from(method);

            //check if accessible
            if (!invokable.isAccessible()) {
                throw new IllegalArgumentException(String
                    .format("Method %s is not accessible on class %s", method, object.getClass()));
            }

            //check if return type is correct
            if (!isValidReturnType(invokable, returnType)) {
                throw new IllegalArgumentException(String
                    .format("Method return type %s is not invokable from return type %s",
                        invokable.getReturnType().getRawType(), returnType));
            }

            //check that it does not required arguments
            if (!invokable.getParameters().isEmpty()) {
                throw new IllegalArgumentException("Get method should not require arguments.");
            }

            try {
                //noinspection unchecked
                return (T) method.invoke(object);
            } catch (IllegalAccessException e) {
                throw new BindingException(String
                    .format("Could not get value on method %s on object %s having class %s.",
                        method.getName(), object, object.getClass()), e);
            } catch (ClassCastException e) {
                throw new BindingException("Could not cast field value.", e);
            } catch (InvocationTargetException e) {
                throw new BindingException(String.format(
                    "Exception occurred while calling method %s on object %s having class %s",
                    method.getName(), object, object.getClass()), e);
            }
        }
    }


}
