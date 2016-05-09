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

package cloud;

import com.google.common.net.HostAndPort;
import de.uniulm.omi.cloudiator.sword.api.domain.LoginCredential;
import de.uniulm.omi.cloudiator.sword.api.domain.OSFamily;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteException;
import de.uniulm.omi.cloudiator.sword.api.service.ConnectionService;
import play.Logger;
import util.logging.Loggers;

/**
 * Created by daniel on 02.09.15.
 */
public class DefaultSwordConnectionService implements SwordConnectionService {

    private final ConnectionService delegate;
    private final static Logger.ALogger LOGGER = Loggers.of(Loggers.CLOUD_REMOTE);

    public DefaultSwordConnectionService(ConnectionService delegate) {
        this.delegate = delegate;
    }

    @Override
    public RemoteConnection getRemoteConnection(HostAndPort hostAndPort, OSFamily osFamily,
        LoginCredential loginCredential) throws RemoteException {

        LOGGER.info(String.format(
            "Creating RemoteConnection to %s, targeting operating system %s and using credentials %s",
            hostAndPort, osFamily, loginCredential));

        return delegate.getRemoteConnection(hostAndPort, osFamily, loginCredential);
    }
}
