/*
 * Copyright (c) 2014-2015 University of Ulm
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

import components.installer.api.InstallApi;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;
import models.Tenant;
import models.VirtualMachine;

/**
 * Created by daniel on 05.08.15.
 */
public class Installers {

    private Installers() {

    }

    public static InstallApi of(RemoteConnection remoteConnection, VirtualMachine virtualMachine,
        Tenant tenant) {
        switch (virtualMachine.operatingSystem().operatingSystemFamily().operatingSystemType()) {
            case LINUX:
                return new UnixInstaller(remoteConnection, virtualMachine, tenant);
            case WINDOWS:
                return new WindowsInstaller(remoteConnection, virtualMachine, tenant);
            default:
                throw new UnsupportedOperationException(String
                    .format("OperatingSystemType %s is not supported by the installation logic",
                        virtualMachine.operatingSystem().operatingSystemFamily()
                            .operatingSystemType()));
        }
    }

}
