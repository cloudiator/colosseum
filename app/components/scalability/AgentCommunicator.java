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


import de.uniulm.omi.cloudiator.visor.client.entities.Monitor;
import models.MonitorInstance;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Frank on 20.07.2015.
 */
public interface AgentCommunicator {

    void addMonitor(String idMonitorInstance, String className, String metricName, long interval,
        TimeUnit unit);

    void removeMonitor(String className, String metricName, long interval, TimeUnit unit);

    void removeMonitorForComponent(String className, String metricName, long interval,
        TimeUnit unit, String componentId);

    void removeMonitor(Monitor monitor);

    void addMonitorForComponent(String idMonitorInstance, String className, String metricName,
        long interval, TimeUnit unit, String componentId);

    List<Monitor> getMonitorWithSameValues(String className, String metricName,
        String componentName);

    void updateMonitor(MonitorInstance mi);

    boolean hasSameContext(Monitor mon, String contextKey, String contextValue);

    Monitor copyValueFromMonitorInstance(Monitor m, MonitorInstance mi);

    int getPort();

    String getIp();

    String getProtocol();
}
