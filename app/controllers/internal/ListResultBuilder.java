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

import java.util.List;

/**
 * Created by daniel on 25.01.16.
 */
public class ListResultBuilder<T> implements ResultBuilder {

    private final List<T> list;
    private final Class<T> tClass;
    private final StatusCode statusCode;

    public ListResultBuilder(StatusCode statusCode, List<T> list, Class<T> tClass) {
        this.list = list;
        this.tClass = tClass;
        this.statusCode = statusCode;
    }

    @Override public Result build() {
        return new ListResponse<>(statusCode, list, tClass).result();
    }
}
