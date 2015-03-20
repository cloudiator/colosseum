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

import dtos.conversion.api.FieldBinding;
import dtos.conversion.api.FromBindingBuilder;
import dtos.conversion.api.ToBindingBuilder;
import dtos.conversion.api.TransformationBindingBuilder;
import dtos.conversion.transformers.Transformer;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 17.03.15.
 */
public class FieldBindingBuilder implements FromBindingBuilder {

    @Nullable public ToBindingBuilderImpl toBindingBuilder;

    public FieldBindingBuilder() {
    }

    @Override public <T> ToBindingBuilder<T> from(Class<T> fieldType, String fieldName) {
        final ToBindingBuilderImpl<T> tToBindingBuilder =
            new ToBindingBuilderImpl<>(fieldType, fieldName);
        this.toBindingBuilder = tToBindingBuilder;
        return tToBindingBuilder;
    }

    @Override public ToBindingBuilder<Object> from(String name) {
        final ToBindingBuilderImpl<Object> tToBindingBuilder =
            new ToBindingBuilderImpl<>(Object.class, name);
        this.toBindingBuilder = tToBindingBuilder;
        return tToBindingBuilder;
    }

    public FieldBinding build() {
        checkState(toBindingBuilder != null);
        return toBindingBuilder.build();
    }

    private class ToBindingBuilderImpl<T> implements ToBindingBuilder<T> {

        private final Class<T> fromFieldType;
        private final String fromFieldName;
        @Nullable private TransformationBindingBuilderImpl transformationBindingBuilder;

        private ToBindingBuilderImpl(Class<T> fromFieldType, String fromFieldName) {
            this.fromFieldType = fromFieldType;
            this.fromFieldName = fromFieldName;
        }

        @Override
        public <S> TransformationBindingBuilder<T, S> to(Class<S> fieldType, String name) {
            final TransformationBindingBuilderImpl<T, S> tsTransformationBindingBuilder =
                new TransformationBindingBuilderImpl<>(fromFieldType, fromFieldName, fieldType,
                    name);
            this.transformationBindingBuilder = tsTransformationBindingBuilder;
            return tsTransformationBindingBuilder;
        }

        @Override public TransformationBindingBuilder<T, Object> to(String name) {
            final TransformationBindingBuilderImpl<T, Object> tObjectTransformationBindingBuilder =
                new TransformationBindingBuilderImpl<>(fromFieldType, fromFieldName, Object.class,
                    name);
            this.transformationBindingBuilder = tObjectTransformationBindingBuilder;
            return tObjectTransformationBindingBuilder;
        }

        private FieldBinding build() {
            checkState(transformationBindingBuilder != null);
            return transformationBindingBuilder.build();
        }

    }


    private class TransformationBindingBuilderImpl<T, S>
        implements TransformationBindingBuilder<T, S> {

        private final Class<T> fromFieldType;
        private final String fromFieldName;
        private final Class<S> toFieldType;
        private final String toFieldName;
        @Nullable private Transformer<T, S> transformer;

        public TransformationBindingBuilderImpl(Class<T> fromFieldType, String fromFieldName,
            Class<S> toFieldType, String toFieldName) {
            this.fromFieldType = fromFieldType;
            this.fromFieldName = fromFieldName;
            this.toFieldType = toFieldType;
            this.toFieldName = toFieldName;
        }

        @Override public void withTransformation(@Nullable Transformer<T, S> transformer) {
            this.transformer = transformer;
        }

        @Override public void withUnsafeTransformation(Transformer transformer) {
            //noinspection unchecked
            this.transformer = transformer;
        }

        private FieldBinding build() {
            if (transformer == null) {
                return new DefaultFieldBinding(fromFieldName, toFieldName);
            }
            return new GenericFieldBinding<>(this.fromFieldType, fromFieldName, toFieldType,
                toFieldName, transformer);
        }
    }
}

