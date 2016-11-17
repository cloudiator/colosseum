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

package util;

/**
 * Created by daniel on 11.11.16.
 */
public class ConfigurationConstants {

    private ConfigurationConstants() {
        throw new AssertionError("Do not instantiate.");
    }

    public final static String NODE_GROUP = "colosseum.nodegroup";
    public final static String SYNC_VIRTUAL_MACHINE_NOT_IN_DATABASE_DETECTOR =
        "colosseum.sync.virtualMachineNotInDatabase.detector";
    public final static String SYNC_VIRTUAL_MACHINE_ERROR_DETECTOR = "colosseum.sync.virtualMachineError.detector";
    public final static String SYNC_INSTANCE_ERROR_DETECTOR = "colosseum.sync.instanceError.detector";
    public final static String DELETE_FAILED_INSTANCES = "colosseum.deleteFailedInstances";

    public final static String RMI_TIMEOUT = "colosseum.rmi.timeout";

}
