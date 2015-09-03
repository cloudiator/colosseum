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

    private final Getter<T> fromGetter;
    private final Setter<T> fromSetter;
    private final Getter<S> toGetter;
    private final Setter<S> toSetter;
    private final Transformer<T, S> transformer;

    public GenericConversionBinding(Getter<T> fromGetter, Setter<T> fromSetter, Getter<S> toGetter,
        Setter<S> toSetter, Transformer<T, S> transformer) {
        this.fromGetter = fromGetter;
        this.fromSetter = fromSetter;
        this.toGetter = toGetter;
        this.toSetter = toSetter;
        this.transformer = transformer;
    }

    @Override public void bind(Object from, Object to) {
        toSetter.setValue(to, this.transformer.transform(fromGetter.getValue(from)));
    }

    @Override public void bindReverse(Object from, Object to) {
        fromSetter.setValue(from, this.transformer.transformReverse(toGetter.getValue(to)));
    }


}
