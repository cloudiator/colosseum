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
import models.scalability.FlowOperator;
import models.scalability.FormulaOperator;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Frank on 20.07.2015.
 */
public interface DatabaseAccess {
    public List<MonitorInstance> getMonitorInstances(Long monitorId);
    public MonitorInstance saveMonitorInstance(Long idMonitor, String apiEndpoint, Long ipAddressId, Long vmId, Long componentId);
    public MonitorInstance getMonitorInstance(Long monitorInstanceId);
    public Collection<MonitorInstance> getMonitorInstances();
    public RawMonitor saveRawMonitor(Long applicationId, Long componentId, Long compInstanceId, Long cloudId, Schedule schedule, SensorDescription config);
    public RawMonitor getRawMonitor(Long monitorId);
    public Collection<RawMonitor> getRawMonitors();
    public ConstantMonitor saveConstantMonitor(Double val);
    public ConstantMonitor getConstantMonitor(Long monitorId);
    public Collection<ConstantMonitor> getConstantMonitors();
    public ComposedMonitor saveComposedMonitor(FlowOperator flow, FormulaQuantifier quantifier, Schedule schedule, Window window, FormulaOperator formulaOperator, List<Monitor> monitors);
    public ComposedMonitor getComposedMonitor(Long monitorId);
    public List<ComposedMonitor> getComposedMonitors();
    public SensorDescription saveSensorDescription(String _className, String _metricName, boolean _isVmSensor);
    public SensorDescription getSensorDescription(SensorDescription sensorDescription);
    public List<SensorDescription> getSensorDescriptions();
    public TimeWindow saveTimeWindow(Long interval, TimeUnit timeUnit);
    public TimeWindow getTimeWindow(Long windowId);
    public List<TimeWindow> getTimeWindows();
    public MeasurementWindow saveMeasurementWindow(Long measurements);
    public MeasurementWindow getMeasurementWindow(Long windowId);
    public List<MeasurementWindow> getMeasurementWindows();
    public Schedule saveSchedule(Long interval, TimeUnit timeUnit);
    public Schedule getSchedule(Long scheduleId);
    public List<Schedule> getSchedules();
    public ComponentHorizontalOutScalingAction saveComponentHorizontalOutScalingAction(Long amount, Long min, Long max, Long count, Long component);
    public ComponentHorizontalOutScalingAction getComponentHorizontalOutScalingAction(Long id);
    public List<ComponentHorizontalOutScalingAction> getComponentHorizontalOutScalingAction();
    public Monitor getMonitor(Long id);
    public void deleteMonitorAndItsInstances(Long id);
    public void setExternalIDToMonitor(Long monitorId, String externalId);
    public void setExternalIDToMonitorInstance(Long monitorInstanceId, String externalId);
    public void setExternalIDToMonitorInstance(Long monitorInstanceId, String externalId, Long virtualMachine);
    public void setExternalIDToMonitorInstance(Long monitorInstanceId, String externalId, Long virtualMachine, Long componentId);
    public Schedule getSchedule(Long interval, TimeUnit timeUnit) throws Exception;
    public SensorDescription getSensorDescription(String _className, String _metricName, boolean _isVmSensor);
    public TimeWindow getTimeWindow(Long interval, TimeUnit timeUnit);
    public List<Monitor> getMonitors();
    public Schedule getLowestSchedule(List<Monitor> monitors) throws Exception;
    public TimeWindow getSmallestTimeWindow(List<Monitor> monitors) throws Exception;
    public ComposedMonitor getComposedMonitorByExternalId(String externalId);
    public void addScalingAction(ComposedMonitor monitor, ScalingAction action);
}
