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

import java.util.List;

import models.ApplicationComponent;
import models.Component;
import models.ComposedMonitor;
import models.Instance;
import models.Monitor;
import models.MonitorInstance;
import models.RawMonitor;
import models.VirtualMachine;

/**
 * Created by Frank on 23.07.2015.
 */
public interface FrontendCommunicator {

    List<VirtualMachine> getVirtualMachines(Long applicationId, Long componentId, Long instanceId, Long cloudId);
    List<Instance> getInstances(Long vm);
    ApplicationComponent getApplicationComponentForInstance(Long appCompId);
    String getPublicAddressOfVM(VirtualMachine vm);
    List<Component> getComponents(Long applicationId, Long componentId, Long instanceId, Long cloudId);
    String getIpAddress(Long idIpAddress);
    Long getIdPublicAddressOfVM(VirtualMachine vm);
    void removeMonitorInstance(MonitorInstance monitorInstance);
    List<MonitorInstance> getMonitorInstances(Long monitorId);
    MonitorInstance saveMonitorInstance(Long idMonitor, String apiEndpoint, Long ipAddressId, Long vmId, Long componentId);
    MonitorInstance saveMonitorInstance(MonitorInstance monitorInstance);
    MonitorInstance getMonitorInstance(Long monitorInstanceId);
    RawMonitor getRawMonitor(Long monitorId);
    ComposedMonitor getComposedMonitor(Long monitorId);
    Monitor getMonitor(Long id);
    List<Monitor> getMonitors();
}