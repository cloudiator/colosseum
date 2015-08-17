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

import models.*;
import models.scalability.FormulaOperator;
import models.scalability.SubscriptionType;

import java.util.List;

/**
 * Created by Frank on 22.06.2015.
 */
public interface ScalingEngine {
    /** monitor all component instances attached to this application Id */
    public Monitor doMonitorComponents(Application applicationId, Schedule schedule, SensorDescription desc);
    /** monitor all component instances attached to this application Id and component Id*/
    public Monitor doMonitorComponents(Application applicationId, Component component, Schedule schedule, SensorDescription desc);
    /** monitor this particular component instance */
    public Monitor doMonitorComponents(Application application, Component component, Instance compInstance, Schedule schedule, SensorDescription desc);

    /** monitor all component instances of this component in this cloud */
    public Monitor doMonitorComponents(Application application, Component componentId, Cloud cloudId, Schedule schedule, SensorDescription desc);
    /** monitor all component instances in this cloud */
    public Monitor doMonitorComponents(Application application, Cloud cloud, Schedule schedule, SensorDescription desc);

    /** all vms of this application */
    public Monitor doMonitorVms(Application appId, Schedule schedule, SensorDescription desc);
    /** all vms of this application in this cloud */
    public Monitor doMonitorVms(Application appId, Cloud cid, Schedule schedule, SensorDescription desc);
    /** all vms of this application running a component instance of this component */
    public Monitor doMonitorVms(Application appId, Component cid, Schedule schedule, SensorDescription desc);
    /** all vms of this application running a component instance of this component in this cloud */
    public Monitor doMonitorVms(Application appId, Component cid, Cloud cloudId, Schedule schedule, SensorDescription desc);


    /** most simple map and reduce **/
    public Monitor mapAggregatedMonitors(FormulaQuantifier quantifier, Schedule schedule, Window window, FormulaOperator formulaOperator, List<Monitor> monitors);
    public Monitor reduceAggregatedMonitors(FormulaQuantifier quantifier, Schedule schedule, Window window, FormulaOperator formulaOperator, List<Monitor> monitors);
    public Monitor aggregateMonitors(ComposedMonitor monitor, boolean createInstances);

    /** remove a monitor */
    public void removeMonitor(long monitorId);

    /** update a monitor */
    public void updateMonitor(RawMonitor monitor);

    /** add an external ID to a monitor / monitor instance based on discriminator */
    public void addExternalIdToMonitor(Long monitorId, String externalId);
    public void addExternalId(Long monitorInstanceId, String externalId);
    public void addExternalId(Long monitorId, String externalId, Long virtualMachine);
    public void addExternalId(Long monitorId, String externalId, Long virtualMachine, Long componentId);

    public Monitor _doMonitorComponents(long applicationId, long componentId, long instanceId, long cloudId, Schedule schedule, SensorDescription sensorDescription);
    public Monitor _doMonitorVms(long applicationId, long componentId, long instanceId, long cloudId, Schedule schedule, SensorDescription sensorDescription);

    public Monitor _doMonitorVms(long applicationId, long componentId, long instanceId, long cloudId, RawMonitor monitor);

    void subscribe(Monitor monitor, MonitorSubscription subscription);
    void unsubscribe(Long idSubscription);
}
