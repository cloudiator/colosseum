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

package components.log;

import cloud.strategies.RemoteConnectionStrategy;
import com.google.common.base.MoreObjects;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteException;
import models.VirtualMachine;
import play.Logger;
import util.logging.Loggers;

import java.io.File;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;

public class RemoteLogCollector implements LogCollector {

    Logger.ALogger LOGGER = Loggers.of(Loggers.LOG_COLLECTION);

    private final VirtualMachine virtualMachine;
    private final String remotePath;
    private final RemoteConnectionStrategy remoteConnectionStrategy;

    public RemoteLogCollector(VirtualMachine virtualMachine, String remotePath,
        RemoteConnectionStrategy remoteConnectionStrategy) {
        this.virtualMachine = virtualMachine;
        this.remotePath = remotePath;
        this.remoteConnectionStrategy = remoteConnectionStrategy;
    }

    @Override public Optional<File> get() {
        try (RemoteConnection remoteConnection = this.remoteConnectionStrategy
            .connect(virtualMachine)) {
            return Optional.of(remoteConnection.downloadFile(remotePath));
        } catch (RemoteException e) {
            LOGGER.warn(String.format("Error while collecting %s", this), e);
            return Optional.empty();
        }
    }

    @Override public String name() {
        String fileName = new File(remotePath).getName();
        checkState(virtualMachine.publicIpAddress().isPresent());
        String Ip = virtualMachine.publicIpAddress().get().getIp();
        return fileName + "-" + Ip;
    }

    @Override public String toString() {
        return MoreObjects.toStringHelper(this).add("virtualMachine", virtualMachine)
            .add("remotePath", remotePath).add("remoteConnectionStrategy", remoteConnectionStrategy)
            .toString();
    }
}
