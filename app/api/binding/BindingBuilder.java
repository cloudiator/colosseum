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

import api.binding.transformers.DefaultTransformer;
import api.binding.transformers.Transformer;

/**
 * Created by daniel on 17.03.15.
 */
public class BindingBuilder<T, S> implements FromBindingBuilder<T, S>, ToBindingBuilder<T, S>,
    TransformationBindingBuilder<T, S> {

    private final Class<T> tClass;
    private final Class<S> sClass;

    private Getter<T> fromGetter;
    private Setter<T> fromSetter;
    private Getter<S> toGetter;
    private Setter<S> toSetter;
    private Transformer<T, S> transformer;

    public BindingBuilder(Class<T> tClass, Class<S> sClass) {
        this.tClass = tClass;
        this.sClass = sClass;
        //noinspection unchecked
        transformer = (Transformer<T, S>) new DefaultTransformer();
    }

    @Override public ToBindingBuilder<T, S> fromField(String name) {
        fromGetter = ReflectionField.of(name, tClass);
        fromSetter = ReflectionField.of(name, tClass);
        return this;
    }

    @Override public ToBindingBuilder<T, S> fromSetterGetter(String name) {
        fromGetter = ReflectionMethods.getGetter(SetterGetterName.of(name).getGetterName(), tClass);
        fromSetter = ReflectionMethods.getSetter(SetterGetterName.of(name).getSetterName(), tClass);
        return this;
    }

    @Override
    public ToBindingBuilder<T, S> fromMethods(String getMethodName, String setMethodName) {
        fromGetter = ReflectionMethods.getGetter(getMethodName, tClass);
        fromSetter = ReflectionMethods.getSetter(setMethodName, tClass);
        return this;
    }

    @Override public TransformationBindingBuilder<T, S> toField(String name) {
        toGetter = ReflectionField.of(name, sClass);
        toSetter = ReflectionField.of(name, sClass);
        return this;
    }

    @Override public TransformationBindingBuilder<T, S> toSetterGetter(String name) {
        toGetter = ReflectionMethods.getGetter(SetterGetterName.of(name).getGetterName(), sClass);
        toSetter = ReflectionMethods.getSetter(SetterGetterName.of(name).getSetterName(), sClass);
        return this;
    }

    @Override public TransformationBindingBuilder<T, S> toMethods(String getMethodName,
        String setMethodName) {
        toGetter = ReflectionMethods.getGetter(getMethodName, sClass);
        toSetter = ReflectionMethods.getSetter(setMethodName, sClass);
        return this;
    }

    @Override public void withTransformation(Transformer<T, S> transformer) {
        this.transformer = transformer;
    }

    public Binding build() {
        return new GenericBinding<>(fromGetter, fromSetter, toGetter, toSetter,
            transformer);
    }
}

