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
import models.MonitorInstance;
import models.RawMonitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Frank on 01.09.2015.
 */
public class ComposedMonitorTsdbLocator implements TsdbLocator{
    private final ComposedMonitor monitor;

    public ComposedMonitorTsdbLocator(ComposedMonitor monitor) {
        this.monitor = monitor;
    }

    @Override public Map<String, List<MonitorInstance>> getTsdbIp() {
        Map<String, List<MonitorInstance>> map = new HashMap<>();
        // TODO just for the time being composed monitors always on localhost
        // null is ok, because in that case just this IP is used
        TsdbHelper.addMonitorInstanceToIP(map, TsdbHelper.DEFAULT_TSDB_HOST, null);
        return map;
    }
}
