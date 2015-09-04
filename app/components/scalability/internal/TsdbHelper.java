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

package components.scalability.internal;

import models.ComposedMonitor;
import models.Monitor;
import models.MonitorInstance;
import models.RawMonitor;
import play.Play;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Frank on 24.08.2015.
 */
public class TsdbHelper {
    public static final String DEFAULT_TSDB_HOST =
        Play.application().configuration().getString("colosseum.scalability.tsdb.host.default");


    private TsdbHelper(){
        //no instantiation of this class
    }

    //TODO use instances as filter - does this make sense?
    public static Map<String, List<MonitorInstance>> getIpOfTSDB(Monitor mon, List<MonitorInstance> instances){
        Map<String, List<MonitorInstance>> result = new HashMap<>();

        if(mon == null){
            // TODO
            // because sometimes for the aggregation e.g. in subscription, the
            // monitor is not available anymore. There must be a way to access
            // the monitor object BEFORE delete is called.
            addMonitorInstanceToIP(result, DEFAULT_TSDB_HOST, null);
        } else {
            result = mon.getTsdbIp();
        }

        return result;
    }

    public static void addMonitorInstanceToIP(Map<String, List<MonitorInstance>> map, String ip, MonitorInstance mi){
        if(!map.containsKey(ip)){
            map.put(ip, new ArrayList<MonitorInstance>());
        }

        if(mi != null){
            map.get(ip).add(mi);
        }
    }
}
