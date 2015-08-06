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

import java.util.List;

/**
 * Created by Frank on 23.07.2015.
 */
public interface FrontendCommunicator extends DatabaseAccess {

    public List<VirtualMachine> getVirtualMachines(long applicationId, long componentId, long instanceId, long cloudId);

    public List<Instance> getInstances(long vm);

    public ApplicationComponent getApplicationComponentForInstance(long appCompId);

    public long getIdFromLink(String link);

    public String getPublicAddressOfVM(VirtualMachine vm);

    public List<LifecycleComponent> getComponents(long applicationId, long componentId, long instanceId, long cloudId);

    public boolean isInstanceOf(Instance instance, List<ApplicationComponent> appComps, LifecycleComponent component);

    public boolean isInstanceOf(Instance instance, List<VirtualMachine> vms, long cloudId);

    public String getIpAddress(long idIpAddress);

    public long getIdPublicAddressOfVM(VirtualMachine vm);

    public Long getVirtualMachineToIP(String ipAddress);

    public Long getApplicationIdByName(String name);

    public Long getComponentIdByName(String name);

    public List<MonitorInstance> getMonitorInstances(long idMonitor);

    public List<Long> getMonitorInstanceIDs(long idMonitor);

    public void removeMonitorInstance(MonitorInstance monitorInstance);
}