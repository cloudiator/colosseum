/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package components.scalability;

import de.uniulm.omi.cloudiator.axe.aggregator.communication.rmi.AggregatorServiceAccess;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import play.Logger;
import play.Play;

/**
 * Created by Frank on 24.08.2015.
 */
public class AggregationAccessService {
    private final static Map<String, AggregatorServiceAccess> services =
        new ConcurrentHashMap<String, AggregatorServiceAccess>();
    private final static Logger.ALogger LOGGER = play.Logger.of("colosseum.scalability");

    public synchronized static AggregatorServiceAccess getService(String ip, int port, String key) {
        return getService(ip, port, key, false);
    }

    public synchronized static AggregatorServiceAccess getService(String ip, int port, String key,
        boolean retry) {

        String mapKey = ip + "_" + port + "_" + key;

        if (!services.containsKey(mapKey)) {
            Registry reg = null;
            try {
                reg = LocateRegistry.getRegistry(ip, port);
                Object o = reg.lookup(key);
                AggregatorServiceAccess asa = (AggregatorServiceAccess) o;

                services.put(mapKey, asa);
            } catch (RemoteException e) {
                LOGGER.error("Error while calling remote object.", e);
            } catch (NotBoundException e) {
                LOGGER.error("Remote object was not bound in registry.", e);
            }
        } else {
            try {
                services.get(mapKey).ping();
            } catch (RemoteException re) { // TODO outsource exception handling into separate client class
                if (retry) {
                    LOGGER.error("Could not locate remote object even after a retry.");
                } else {
                    services.remove(mapKey);

                    return getService(ip, port, key, true);
                }
            }
        }

        return services.get(mapKey);
    }

    public synchronized static AggregatorServiceAccess getLocalService() {

        final String LOCALHOST = Play.application().configuration()
            .getString("colosseum.scalability.aggregator.rmi.host");
        //TODO: use RMI default port on home domain?
        //final int RMI_PORT =
        //    Play.application().configuration().getInt("colosseum.scalability.aggregator.rmi.port");
        final int RMI_PORT = Registry.REGISTRY_PORT;
        final String RMI_KEY = Play.application().configuration()
            .getString("colosseum.scalability.aggregator.rmi.key");

        return getService(LOCALHOST, RMI_PORT, RMI_KEY);
    }
}
