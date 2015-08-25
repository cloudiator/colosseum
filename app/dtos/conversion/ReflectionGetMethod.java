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
import java.lang.reflect.Method;

/**
 * Created by daniel on 25.08.15.
 */
public class ReflectionGetMethod<T> {



    public ReflectionGetMethod(String methodName, Class<T> returnType, Object o) {

        Method method = this.getMethod(o.getClass(), methodName);

        if (method == null) {
            throw new BindingException(String
                .format("Method %s does not exist on object %s of class %s", methodName, o,
                    o.getClass()));
        }
        final Invokable<?, Object> invokable = Invokable.from(method);

        //check if accessible
        if(!invokable.isAccessible()) {
            throw new BindingException("");
        }

    }

    @Nullable private Method getMethod(Class clazz, String methodToFind) {
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


}
