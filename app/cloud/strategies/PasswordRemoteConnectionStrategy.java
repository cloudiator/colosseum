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

package cloud.strategies;

import cloud.SwordConnectionService;
import com.google.common.net.HostAndPort;
import com.google.inject.Inject;
import de.uniulm.omi.cloudiator.sword.api.domain.OSFamily;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteException;
import de.uniulm.omi.cloudiator.sword.core.domain.LoginCredentialBuilder;
import models.VirtualMachine;

import static com.google.common.base.Preconditions.*;

/**
 * Created by daniel on 01.09.15.
 */
public class PasswordRemoteConnectionStrategy implements RemoteConnectionStrategy {

    private final SwordConnectionService connectionService;

    private PasswordRemoteConnectionStrategy(SwordConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @Override public RemoteConnection connect(VirtualMachine virtualMachine)
        throws RemoteException {

        checkState(virtualMachine.owner().isPresent(),
            "Owner of virtual machine should be set before calling connect.");

        checkArgument(virtualMachine.publicIpAddress().isPresent(),
            "Virtual machine must have a public ip address.");

        checkArgument(virtualMachine.loginName().isPresent(),
            "Virtual machine must have a login name.");

        if (!virtualMachine.loginPassword().isPresent()) {
            throw new RemoteException(String
                .format("Virtual machine %s does not provide a login password", virtualMachine));
        }

        int remotePort = virtualMachine.remotePortOrDefault();
        OSFamily osFamily = virtualMachine.operatingSystemVendorTypeOrDefault().osFamily();

        return connectionService.getRemoteConnection(
            HostAndPort.fromParts(virtualMachine.publicIpAddress().get().getIp(), remotePort),
            osFamily,
            LoginCredentialBuilder.newBuilder().password(virtualMachine.loginPassword().get())
                .username(virtualMachine.loginName().get()).build());

    }

    @Override public int getPriority() {
        return Priority.LOW;
    }

    public static class PasswordRemoteConnectionStrategyFactory
        implements RemoteConnectionStrategyFactory {

        private final SwordConnectionService connectionService;

        @Inject
        public PasswordRemoteConnectionStrategyFactory(SwordConnectionService connectionService) {

            checkNotNull(connectionService);

            this.connectionService = connectionService;
        }

        @Override public RemoteConnectionStrategy create() {
            return new PasswordRemoteConnectionStrategy(connectionService);
        }
    }

}
