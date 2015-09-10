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

import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;
import models.Tenant;
import models.VirtualMachine;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by daniel on 02.09.15.
 */
public class RemoteConnectionStrategies implements RemoteConnectionStrategy {

    private final Set<RemoteConnectionStrategy> strategySet;

    private RemoteConnectionStrategies(Set<RemoteConnectionStrategy> strategySet) {
        this.strategySet = strategySet;
    }

    @Override public boolean isApplicable(VirtualMachine virtualMachine) {
        return strategySet.stream().anyMatch(
            remoteConnectionStrategy -> remoteConnectionStrategy.isApplicable(virtualMachine));
    }

    @Override public RemoteConnection apply(VirtualMachine virtualMachine) {


        final Set<RemoteConnectionStrategy> applicableStrategies = strategySet.stream().filter(
            remoteConnectionStrategy -> remoteConnectionStrategy.isApplicable(virtualMachine))
            .collect(Collectors.toSet());

        Exception lastException = null;
        for (RemoteConnectionStrategy remoteConnectionStrategy : applicableStrategies) {
            try {
                return remoteConnectionStrategy.apply(virtualMachine);
            } catch (Exception last) {
                lastException = last;
            }
        }

        throw new IllegalStateException(
            "Tried all available remote connection strategies, but still could not connect to machine.",
            lastException);
    }

    public static class RemoteConnectionStrategiesFactory
        implements RemoteConnectionStrategyFactory {

        private final Set<RemoteConnectionStrategyFactory> remoteConnectionStrategyFactories;

        public RemoteConnectionStrategiesFactory(
            Set<RemoteConnectionStrategyFactory> remoteConnectionStrategyFactories) {
            this.remoteConnectionStrategyFactories = remoteConnectionStrategyFactories;
        }

        @Override public RemoteConnectionStrategy create(Tenant tenant) {
            Set<RemoteConnectionStrategy> strategies =
                remoteConnectionStrategyFactories.stream().map(factory -> factory.create(tenant))
                    .collect(Collectors.toSet());
            return new RemoteConnectionStrategies(strategies);
        }
    }
}
