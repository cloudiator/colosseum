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

package cloud.resources;

import de.uniulm.omi.cloudiator.sword.api.domain.LoginCredential;
import de.uniulm.omi.cloudiator.sword.api.domain.VirtualMachine;
import models.Cloud;
import models.CloudCredential;
import models.service.LocationModelService;
import models.service.ModelService;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * Created by daniel on 28.05.15.
 */
public class VirtualMachineInLocation extends ResourceWithCredential implements VirtualMachine {

    private final VirtualMachine virtualMachine;

    public VirtualMachineInLocation(VirtualMachine virtualMachine, String cloud, String credential,
        ModelService<Cloud> cloudModelService,
        ModelService<CloudCredential> cloudCredentialModelService) {
        super(virtualMachine, cloud, credential, cloudModelService, cloudCredentialModelService);
        this.virtualMachine = virtualMachine;
    }

    @Override public Set<String> publicAddresses() {
        return virtualMachine.publicAddresses();
    }

    @Override public Set<String> privateAddresses() {
        return virtualMachine.privateAddresses();
    }

    @Override public java.util.Optional<LoginCredential> loginCredential() {
        return virtualMachine.loginCredential();
    }
}
