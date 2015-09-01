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

import de.uniulm.omi.executionware.srl.aggregator.communication.rmi.AggregatorServiceAccess;
import de.uniulm.omi.executionware.srl.aggregator.communication.rmi.Constants;
import play.Logger;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Frank on 24.08.2015.
 */
public class AggregationAccessService {
    private final static Map<String, AggregatorServiceAccess> services = new ConcurrentHashMap<String, AggregatorServiceAccess>();
    private final static Logger.ALogger LOGGER = play.Logger.of("colosseum.scalability");

    public static AggregatorServiceAccess getService(String ip, int port, String key){

        String mapKey = ip + "_" + port + "_" + key;

        if(!services.containsKey(mapKey)){
            Registry reg = null;
            try {
                reg = LocateRegistry.getRegistry(ip, port);
                Object o = reg.lookup(key);
                AggregatorServiceAccess asa = (AggregatorServiceAccess) o;

                services.put(mapKey, asa);
            } catch (RemoteException e) {
                Logger.error("Error while calling remote object: " + e.getMessage());
            } catch (NotBoundException e) {
                Logger.error("Remote object was not bound in registry: " + e.getMessage());
            }
        }

        return services.get(mapKey);
    }
}
