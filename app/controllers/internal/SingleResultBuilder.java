/*
 * Copyright (c) 2014-2016 University of Ulm
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

package controllers.internal;

import play.mvc.Result;

/**
 * Created by daniel on 25.01.16.
 */
public class SingleResultBuilder<T> implements ResultBuilder {

    private final StatusCode statusCode;
    private final T t;
    private final Class<T> tClass;

    public SingleResultBuilder(StatusCode statusCode, T t, Class<T> tClass) {
        this.tClass = tClass;
        this.t = t;
        this.statusCode = statusCode;
    }

    @Override public Result build() {
        return new SingleResponse<>(statusCode, t, tClass).result();
    }
}
