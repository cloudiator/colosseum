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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

/**
 * Created by daniel on 19.01.16.
 */
public class SingleResponse<T> implements Response {

    private final StatusCode statusCode;
    private final T payLoad;
    private final Class<T> type;

    public SingleResponse(StatusCode statusCode, T payLoad, Class<T> type) {
        this.statusCode = statusCode;
        this.payLoad = payLoad;
        this.type = type;
    }

    @Override public Result result() {
        return Results.status(statusCode.statusCode(), toJson(payLoad));
    }

    private JsonNode toJson(T payLoad) {
        try {
            return Json.parse(new ObjectMapper().writerWithType(type).writeValueAsString(payLoad));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
