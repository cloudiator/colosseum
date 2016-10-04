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
import com.google.inject.Inject;
import models.VirtualMachine;
import play.Configuration;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 16.06.16.
 */
public class LanceLogFile extends VMLogFile {

    private final String lanceFileName;

    @Inject LanceLogFile(Configuration configuration,
        RemoteConnectionStrategy.RemoteConnectionStrategyFactory remoteConnectionStrategyFactory) {
        super(remoteConnectionStrategyFactory);
        checkNotNull(configuration, "configuration is null");
        lanceFileName = configuration.getString("colosseum.log.lanceFileName");
        checkState(lanceFileName != null, "colosseum.log.lanceFileName not configured");
    }

    @Override protected String location(VirtualMachine virtualMachine) {
        String homeDir =
            virtualMachine.operatingSystem().operatingSystemFamily().operatingSystemType()
                .homeDirFunction().apply(virtualMachine.loginName());
        return homeDir + "/" + lanceFileName;
    }
}
