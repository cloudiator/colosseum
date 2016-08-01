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

package components.installer;

import java.util.HashSet;
import java.util.Set;

/**
 * @todo installers need to be able to register those ports
 */
public class ToolPorts {

    private ToolPorts() {
        throw new AssertionError("static only");
    }

    static String ports = "22," + //ssh
        "8080," + //kairos
        "31415," +  //visor
        "1099," +  //lance rmi registry
        "33033"; // lance rmi

    public static Set<Integer> inBoundPorts() {
        Set<Integer> intPorts = new HashSet<>();
        for (String port : ports.split(",")) {
            intPorts.add(Integer.valueOf(port));
        }
        return intPorts;
    }

}

