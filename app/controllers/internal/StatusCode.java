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
public enum StatusCode {
    OK(200),
    INTERNAL_SERVER_ERROR(500),
    NOT_FOUND(400);

    private final int statusCode;

    StatusCode(int i) {
        this.statusCode = i;
    }

    public int statusCode() {
        return statusCode;
    }
}
