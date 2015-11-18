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

import models.ComposedMonitor;
import models.Monitor;
import models.MonitorSubscription;
import models.RawMonitor;

/**
 * Created by Frank on 22.06.2015.
 */
public interface ScalingEngine {

    /**
     * most simple map and reduce
     **/
    Monitor aggregateMonitors(ComposedMonitor monitor, boolean createInstances);

    void updateAggregation(ComposedMonitor monitor);

    /**
     * remove a monitor
     */
    void removeMonitor(long monitorId);

    /**
     * update a monitor
     */
    void updateMonitor(RawMonitor monitor);

    /**
     * add an external ID to a monitor / monitor instance based on discriminator
     */
    void addExternalIdToMonitor(Long monitorId, String externalId);

    void addExternalId(Long monitorInstanceId, String externalId);

    void addExternalId(Long monitorId, String externalId, Long virtualMachine);

    void addExternalId(Long monitorId, String externalId, Long virtualMachine, Long componentId);

    Monitor doMonitor(RawMonitor monitor);

    Monitor doMonitorComponents(RawMonitor monitor);

    Monitor doMonitorVms(RawMonitor monitor);

    void subscribe(Monitor monitor, MonitorSubscription subscription);

    void unsubscribe(Long idSubscription);
}
