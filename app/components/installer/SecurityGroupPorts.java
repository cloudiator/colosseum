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

import de.uniulm.omi.cloudiator.common.os.OperatingSystemType;
import de.uniulm.omi.cloudiator.common.os.RemotePortProvider;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @todo installers need to be able to register those ports
 */
public class SecurityGroupPorts {

    private SecurityGroupPorts() {
        throw new AssertionError("static only");
    }

    private static String toolPorts = "8080," + //kairos
        "31415," +  //visor
        "1099," +  //lance rmi registry
        "33033"; // lance rmi

    public static Set<Integer> inBoundPorts() {
        Set<Integer> inboundPorts = new HashSet<>();
        inboundPorts.addAll(remotePorts());
        inboundPorts.addAll(toolPorts());
        return inboundPorts;
    }

    private static Set<Integer> remotePorts() {
        return Arrays.stream(OperatingSystemType.values()).filter(operatingSystemType -> {
            try {
                operatingSystemType.remotePort();
            } catch (RemotePortProvider.UnknownRemotePortException ignored) {
                return false;
            }
            return true;
        }).map(OperatingSystemType::remotePort).collect(Collectors.toSet());
    }

    private static Set<Integer> toolPorts() {
        Set<Integer> intPorts = new HashSet<>();
        for (String port : toolPorts.split(",")) {
            intPorts.add(Integer.valueOf(port));
        }
        return intPorts;
    }



}

