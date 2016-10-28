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

package components.auth;

/**
 * Created by daniel on 05.10.16.
 */
interface TokenValidity {

    long INFINITE_VALIDITY = 0;
    long MAX_VALIDITY = Long.MAX_VALUE;

    /**
     * Checks if the given token is expired.
     *
     * @param token the token to check.
     * @return true if the token has expired, false if not.
     */
    boolean isExpired(Token token);

    /**
     * Amount of time a token is valid.
     *
     * @return time in milliseconds that the token is valid for or {@link TokenValidity#INFINITE_VALIDITY} if
     * token never expires.
     */
    long validity();

    /**
     * The timestamp from now until which all tokens will be valid.
     *
     * @return the timestamp in milliseconds, until which all now create tokens are valid.
     */
    long deadline();

}
