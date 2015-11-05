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

package cloud.config;

import cloud.*;
import cloud.strategies.*;
import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import de.uniulm.omi.cloudiator.sword.remote.internal.RemoteBuilder;
import de.uniulm.omi.cloudiator.sword.remote.overthere.OverthereModule;

/**
 * Created by daniel on 28.04.15.
 */
public class CloudModule extends AbstractModule {

    @Override protected void configure() {

        bind(KeyPairStrategy.class).to(RetrieveOrCreateKeyPair.class);
        bind(ComputeServiceFactory.class).to(SwordComputeServiceFactory.class);
        bind(CloudService.class).to(DefaultCloudService.class);
    }

    @Provides public SwordConnectionService provideConnectionService() {
        return new DefaultSwordConnectionService(
            RemoteBuilder.newBuilder().loggingModule(new SwordLoggingModule())
                .remoteModule(new OverthereModule()).build());
    }

    @Provides
    public RemoteConnectionStrategy.RemoteConnectionStrategyFactory provideConnectionFactory(
        Injector injector) {
        return new CompositeRemoteConnectionStrategy.RemoteConnectionStrategiesFactory(Sets.newHashSet(
            injector.getInstance(
                KeyPairRemoteConnectionStrategy.KeyPairRemoteConnectionStrategyFactory.class),
            injector.getInstance(
                PasswordRemoteConnectionStrategy.PasswordRemoteConnectionStrategyFactory.class)));
    }



}
