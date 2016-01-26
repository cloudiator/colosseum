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

/**
 * Created by daniel on 19.01.16.
 */
public class Error {

    private final String code;
    private final String message;

    private Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorBuilder builder() {
        return new ErrorBuilder();
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public static class ErrorBuilder {

        private String code;
        private String message;

        private ErrorBuilder() {

        }

        public ErrorBuilder code(String code) {
            this.code = code;
            return this;
        }

        public ErrorBuilder message(String message) {
            this.message = message;
            return this;
        }

        public Error build() {
            return new Error(code, message);
        }

    }
}
