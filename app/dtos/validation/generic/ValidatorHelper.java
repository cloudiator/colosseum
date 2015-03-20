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

package dtos.validation.generic;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 12.03.15.
 */
public class ValidatorHelper {

    private ValidatorHelper() {

    }

    /**
     * Checks if the given ipAddress is a valid ip address
     * with respect to ipV4.
     * <p>
     * Licensed under Apache 2.0 License, copyright by Google
     *
     * @param ipAddress the string to be checked. Mandatory.
     * @return true if the string is a valid ipV4 address, false otherwise
     * @throws java.lang.NullPointerException if the ipAddress is null
     * @see <a href="https://developers.google.com/web/fundamentals/input/form/provide-real-time-validation">Google WebFundamentals</a>
     */
    public static boolean isIpV4Address(String ipAddress) {
        checkNotNull(ipAddress);
        return ipAddress.matches(
            "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
    }

    /**
     * Checks if the given string is a valid ip address
     * with respect to ipV6.
     * <p>
     * Licensed under Apache 2.0 License, copyright by Google
     *
     * @param ipAddress the string to be checked. Mandatory.
     * @return true if the string is a valid ipV6 address, false otherwise
     * @throws java.lang.NullPointerException if the ipAddress is null
     * @see <a href="https://developers.google.com/web/fundamentals/input/form/provide-real-time-validation">Google WebFundamentals</a>
     */
    public static boolean isIpV6Address(String ipAddress) {
        checkNotNull(ipAddress);
        return ipAddress.matches(
            "^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]).){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]).){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$");
    }

    /**
     * Checks if the given string is either a valid ipV4 address or a valid ipV6 address.
     *
     * @param ipAddress the string to be checked. Mandatory.
     * @return true if the string is a valid ip address (ipV4 || ipV6), otherwise false.
     * @throws java.lang.NullPointerException if the ipAddress is null
     */
    public static boolean isValidIpAddress(String ipAddress) {
        checkNotNull(ipAddress);
        return isIpV4Address(ipAddress) || isIpV6Address(ipAddress);
    }

}
