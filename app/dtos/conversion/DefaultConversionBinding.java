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

import dtos.conversion.transformers.DefaultTransformer;
import dtos.conversion.transformers.Transformer;

/**
 * Created by daniel on 17.03.15.
 */
public class DefaultConversionBinding extends GenericConversionBinding<Object, Object> {

    public DefaultConversionBinding(String fieldNameFrom, String fieldNameTo) {
        super(Object.class, fieldNameFrom, Object.class, fieldNameTo, new DefaultTransformer());
    }

    public DefaultConversionBinding(Class<Object> fieldTypeFrom, String fieldNameFrom,
        Class<Object> fieldTypeTo, String fieldNameTo, Transformer<Object, Object> transformer) {
        super(fieldTypeFrom, fieldNameFrom, fieldTypeTo, fieldNameTo, transformer);
    }
}
