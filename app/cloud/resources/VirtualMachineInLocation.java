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

import com.google.common.base.Optional;
import de.uniulm.omi.cloudiator.sword.api.domain.LoginCredential;
import de.uniulm.omi.cloudiator.sword.api.domain.VirtualMachine;

import java.util.Set;

/**
 * Created by daniel on 28.05.15.
 */
public class VirtualMachineInLocation extends AbstractLocationScopedResource<VirtualMachine>
    implements VirtualMachine {

    private final VirtualMachine virtualMachine;

    public VirtualMachineInLocation(VirtualMachine resource, String cloud, String credential) {
        super(resource, cloud, credential);
        this.virtualMachine = resource;
    }

    @Override public Set<String> publicAddresses() {
        return virtualMachine.publicAddresses();
    }

    @Override public Set<String> privateAddresses() {
        return virtualMachine.privateAddresses();
    }

    @Override public Optional<LoginCredential> loginCredential() {
        return virtualMachine.loginCredential();
    }
}
