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

import dtos.conversion.transformers.Transformer;

/**
 * Created by daniel on 16.03.15.
 */
public class GenericConversionBinding<T, S> implements ConversionBinding {

    private final String fieldNameFrom;
    private final String fieldNameTo;
    private final Transformer<T, S> transformer;
    private final Class<T> fieldTypeFrom;
    private final Class<S> fieldTypeTo;

    public GenericConversionBinding(Class<T> fieldTypeFrom, String fieldNameFrom,
        Class<S> fieldTypeTo, String fieldNameTo, Transformer<T, S> transformer) {
        this.fieldNameFrom = fieldNameFrom;
        this.fieldNameTo = fieldNameTo;
        this.transformer = transformer;
        this.fieldTypeFrom = fieldTypeFrom;
        this.fieldTypeTo = fieldTypeTo;
    }

    @Override public void bind(Object from, Object to) {
        ReflectionField<T> fromField = ReflectionField.of(fieldNameFrom, fieldTypeFrom, from);
        ReflectionField<S> toField = ReflectionField.of(fieldNameTo, fieldTypeTo, to);
        toField.setValue(this.transformer.transform(fromField.getValue()));
    }

    @Override public void bindReverse(Object from, Object to) {
        ReflectionField<T> fromField = ReflectionField.of(fieldNameFrom, fieldTypeFrom, from);
        ReflectionField<S> toField = ReflectionField.of(fieldNameTo, fieldTypeTo, to);
        fromField.setValue(this.transformer.transformReverse(toField.getValue()));
    }


}
