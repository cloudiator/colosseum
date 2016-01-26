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
public class ErrorResponseBuilderImpl
    implements ErrorCodeBuilder, ErrorMessageBuilder, ResultBuilder {

    private final StatusCode statusCode;
    private String errorCode;
    private String errorMessage;

    public ErrorResponseBuilderImpl(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    @Override public ErrorMessageBuilder errorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    @Override public ResultBuilder message(String message) {
        this.errorMessage = message;
        return this;
    }


    @Override public Result build() {
        return new ErrorResponse(statusCode,
            Error.builder().code(errorCode).message(errorMessage).build()).result();
    }
}
