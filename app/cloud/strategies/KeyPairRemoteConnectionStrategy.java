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
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteException;
import de.uniulm.omi.cloudiator.sword.core.domain.LoginCredentialBuilder;
import models.KeyPair;
import models.Tenant;
import models.VirtualMachine;
import models.service.KeyPairModelService;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 31.08.15.
 */
public class KeyPairRemoteConnectionStrategy implements RemoteConnectionStrategy {

    private final SwordConnectionService connectionService;
    private final Tenant tenant;
    private final KeyPairModelService keyPairModelService;

    private KeyPairRemoteConnectionStrategy(SwordConnectionService connectionService, Tenant tenant,
        KeyPairModelService keyPairModelService) {

        checkNotNull(keyPairModelService);
        checkNotNull(connectionService);
        checkNotNull(tenant);

        this.keyPairModelService = keyPairModelService;
        this.connectionService = connectionService;
        this.tenant = tenant;
    }

    @Override public boolean isApplicable(VirtualMachine virtualMachine) {
        return virtualMachine.supportsKeyPair() && (
            keyPairModelService.getKeyPair(virtualMachine.cloud(), tenant).isPresent()
                || virtualMachine.loginPrivateKey().isPresent());
    }

    @Override public RemoteConnection apply(VirtualMachine virtualMachine) {

        checkArgument(virtualMachine.supportsKeyPair());
        checkArgument(virtualMachine.publicIpAddress().isPresent());
        checkArgument(virtualMachine.loginName().isPresent());

        String privateKey;
        final Optional<KeyPair> keyPair =
            keyPairModelService.getKeyPair(virtualMachine.cloud(), tenant);
        if (keyPair.isPresent()) {
            privateKey = keyPair.get().getPrivateKey();
        } else if (virtualMachine.loginPrivateKey().isPresent()) {
            privateKey = virtualMachine.loginPrivateKey().get();
        } else {
            throw new IllegalStateException(String
                .format("%s was called, even if its not applicable for %s", this, virtualMachine));
        }

        try {
            return connectionService.getRemoteConnection(HostAndPort
                    .fromParts(virtualMachine.publicIpAddress().get().getIp(),
                        virtualMachine.remotePortOrDefault()),
                virtualMachine.operatingSystemVendorTypeOrDefault().osFamily(),
                LoginCredentialBuilder.newBuilder().username(virtualMachine.loginName().get())
                    .privateKey(privateKey).build());
        } catch (RemoteException e) {
            throw new RemoteRuntimeException(e);
        }
    }

    @Override public int getPriority() {
        return Priority.HIGH;
    }

    public static class KeyPairRemoteConnectionStrategyFactory
        implements RemoteConnectionStrategyFactory {

        private final SwordConnectionService connectionService;
        private final KeyPairModelService keyPairModelService;

        @Inject
        public KeyPairRemoteConnectionStrategyFactory(SwordConnectionService connectionService,
            KeyPairModelService keyPairModelService) {

            checkNotNull(connectionService);
            checkNotNull(keyPairModelService);

            this.connectionService = connectionService;
            this.keyPairModelService = keyPairModelService;

        }

        @Override public RemoteConnectionStrategy create(Tenant tenant) {
            return new KeyPairRemoteConnectionStrategy(connectionService, tenant,
                keyPairModelService);
        }
    }
}
