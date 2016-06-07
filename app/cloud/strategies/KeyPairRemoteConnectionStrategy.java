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

import cloud.CompositeCloudPropertyProvider;
import cloud.DefaultSwordConnectionService;
import cloud.SwordLoggingModule;
import com.google.common.net.HostAndPort;
import com.google.inject.Inject;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteException;
import de.uniulm.omi.cloudiator.sword.core.domain.LoginCredentialBuilder;
import de.uniulm.omi.cloudiator.sword.core.properties.PropertiesBuilder;
import de.uniulm.omi.cloudiator.sword.remote.internal.RemoteBuilder;
import de.uniulm.omi.cloudiator.sword.remote.overthere.OverthereModule;
import models.KeyPair;
import models.VirtualMachine;

import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.*;

/**
 * Created by daniel on 31.08.15.
 */
public class KeyPairRemoteConnectionStrategy implements RemoteConnectionStrategy {

    private final KeyPairStrategy keyPairStrategy;

    private KeyPairRemoteConnectionStrategy(KeyPairStrategy keyPairStrategy) {

        checkNotNull(keyPairStrategy);

        this.keyPairStrategy = keyPairStrategy;
    }

    @Override public RemoteConnection connect(VirtualMachine virtualMachine)
        throws RemoteException {

        if (!virtualMachine.supportsKeyPair()) {
            throw new RemoteException(
                String.format("Virtual machine %s does not support key pairs.", virtualMachine));
        }

        checkState(virtualMachine.owner().isPresent(),
            "Owner of virtual machine should be set before calling connect.");

        checkArgument(virtualMachine.publicIpAddress().isPresent(),
            "Virtual machine must have a public ip address.");

        checkArgument(virtualMachine.loginName().isPresent(),
            "Virtual machine must have a login name.");

        String privateKey;
        final Optional<KeyPair> keyPair = keyPairStrategy.retrieve(virtualMachine);
        if (keyPair.isPresent()) {
            privateKey = keyPair.get().getPrivateKey();
        } else if (virtualMachine.loginPrivateKey().isPresent()) {
            privateKey = virtualMachine.loginPrivateKey().get();
        } else {
            throw new RemoteException(String
                .format("%s could not retrieve a key pair for virtual machine %s", this,
                    virtualMachine));
        }

        Map<String, Object> properties =
            new CompositeCloudPropertyProvider(virtualMachine.cloud()).properties();

        return new DefaultSwordConnectionService(
            RemoteBuilder.newBuilder().loggingModule(new SwordLoggingModule())
                .remoteModule(new OverthereModule())
                .properties(PropertiesBuilder.newBuilder().putProperties(properties).build())
                .build()).getRemoteConnection(HostAndPort
                .fromParts(virtualMachine.publicIpAddress().get().getIp(),
                    virtualMachine.remotePortOrDefault()),
            virtualMachine.operatingSystemVendorTypeOrDefault().osFamily(),
            LoginCredentialBuilder.newBuilder().username(virtualMachine.loginName().get())
                .privateKey(privateKey).build());
    }

    @Override public int getPriority() {
        return Priority.HIGH;
    }

    public static class KeyPairRemoteConnectionStrategyFactory
        implements RemoteConnectionStrategyFactory {

        private final KeyPairStrategy keyPairStrategy;

        @Inject public KeyPairRemoteConnectionStrategyFactory(KeyPairStrategy keyPairStrategy) {

            checkNotNull(keyPairStrategy);

            this.keyPairStrategy = keyPairStrategy;

        }

        @Override public RemoteConnectionStrategy create() {
            return new KeyPairRemoteConnectionStrategy(keyPairStrategy);
        }
    }
}
