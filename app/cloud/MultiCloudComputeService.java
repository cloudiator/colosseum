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

package cloud;

import com.google.common.base.Optional;
import com.google.common.net.HostAndPort;
import de.uniulm.omi.executionware.api.domain.*;
import de.uniulm.omi.executionware.api.domain.VirtualMachine;
import de.uniulm.omi.executionware.api.domain.VirtualMachineTemplate;
import de.uniulm.omi.executionware.api.extensions.PublicIpService;
import de.uniulm.omi.executionware.api.service.ComputeService;
import de.uniulm.omi.executionware.api.ssh.SshConnection;

import javax.annotation.Nullable;

/**
 * Created by daniel on 12.03.15.
 */
public interface MultiCloudComputeService extends ComputeService {

    @Nullable
    @Override
    ImageInCloudAndLocation getImage(String s);

    @Nullable
    @Override
    VirtualMachineInCloudAndLocation getVirtualMachine(String s);

    @Nullable
    @Override
    LocationInCloud getLocation(String s);

    @Nullable
    @Override
    HardwareInCloudAndLocation getHardwareFlavor(String s);

    @Override
    Iterable<HardwareFlavor> listHardwareFlavors();

    @Override
    Iterable<Image> listImages();

    @Override
    Iterable<Location> listLocations();

    @Override
    Iterable<VirtualMachine> listVirtualMachines();

    @Override
    void deleteVirtualMachine(String s);

    @Override
    VirtualMachineInCloudAndLocation createVirtualMachine(VirtualMachineTemplate virtualMachineTemplate);

    @Override
    SshConnection getSshConnection(HostAndPort hostAndPort);

    @Override
    Optional<PublicIpService> getPublicIpService();
}
