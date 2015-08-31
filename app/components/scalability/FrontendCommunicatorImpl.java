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

import com.google.inject.Inject;
import de.uniulm.omi.executionware.srl.aggregator.utils.Check;
import models.*;
import models.scalability.FlowOperator;
import models.scalability.FormulaOperator;
import models.service.ModelService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Frank on 20.07.2015.
 */
public class FrontendCommunicatorImpl implements FrontendCommunicator {
    private ModelService<Component> componentModelService;
    private ModelService<SensorDescription> sensorDescriptionModelService;
    private ModelService<Schedule> scheduleModelService;
    private ModelService<RawMonitor> rawMonitorModelService;
    private ModelService<ComposedMonitor> composedMonitorModelService;
    private ModelService<Monitor> monitorModelService;
    private ModelService<MonitorInstance> monitorInstanceModelService;
    private ModelService<VirtualMachine> virtualMachineModelService;
    private ModelService<IpAddress> ipAddressModelService;
    private ModelService<ApplicationComponent> applicationComponentModelService;
    private ModelService<Instance> instanceModelService;


    @Inject
    public FrontendCommunicatorImpl(ModelService<Component> componentModelService,
        ModelService<SensorDescription> sensorDescriptionModelService,
        ModelService<Schedule> scheduleModelService,
        ModelService<RawMonitor> rawMonitorModelService,
        ModelService<ComposedMonitor> composedMonitorModelService,
        ModelService<Monitor> monitorModelService,
        ModelService<MonitorInstance> monitorInstanceModelService,
        ModelService<VirtualMachine> virtualMachineModelService,
        ModelService<IpAddress> ipAddressModelService,
        ModelService<ApplicationComponent> applicationComponentModelService,
        ModelService<Instance> instanceModelService) {
        this.componentModelService = componentModelService;
        this.sensorDescriptionModelService = sensorDescriptionModelService;
        this.scheduleModelService = scheduleModelService;
        this.rawMonitorModelService = rawMonitorModelService;
        this.composedMonitorModelService = composedMonitorModelService;
        this.monitorModelService = monitorModelService;
        this.monitorInstanceModelService = monitorInstanceModelService;
        this.virtualMachineModelService = virtualMachineModelService;
        this.ipAddressModelService = ipAddressModelService;
        this.applicationComponentModelService = applicationComponentModelService;
        this.instanceModelService = instanceModelService;
    }

    @Override public List<VirtualMachine> getVirtualMachines(Long applicationId, Long componentId,
        Long instanceId, Long cloudId) {
        List<VirtualMachine> vms;
        List<VirtualMachine> result = new ArrayList<VirtualMachine>();

        vms = virtualMachineModelService.getAll();

        for(VirtualMachine vm : vms) {
            boolean suitable = true;
            List<Instance> instances = null;
            List<ApplicationComponent> appComps = null;


            // Filter for application id
            if (applicationId != null) {
                instances = this.getInstances(vm.getId());
                appComps = new ArrayList<ApplicationComponent>();
                for(Instance instance : instances){
                    if (instance.getVirtualMachine().getId().equals(vm.getId())){
                        System.out.println("Instance " + instance.getId() + " belongs to VM " + vm.getId());
                        appComps.add(getApplicationComponentForInstance(instance.getApplicationComponent().getId()));
                    }
                }

                boolean oneInstanceFit = false;

                for(ApplicationComponent ac : appComps){
                    if(ac.getApplication().getId() == applicationId){
                        oneInstanceFit = true;
                    }
                }

                suitable = oneInstanceFit;
            }

            // Filter for component id
            if (suitable && componentId != null) {
                if (instances == null){
                    instances = this.getInstances(vm.getId());
                    appComps = new ArrayList<ApplicationComponent>();
                    for(Instance instance : instances){
                        appComps.add(getApplicationComponentForInstance(instance.getApplicationComponent().getId()));
                    }
                }

                boolean oneInstanceFit = false;

                for(ApplicationComponent ac : appComps){
                    if(ac.getComponent().getId() == componentId){
                        oneInstanceFit = true;
                    }
                }

                suitable = oneInstanceFit;
            }

            // Filter for instance id
            if (suitable && instanceId != null) {
                if (instances == null){
                    instances = this.getInstances(vm.getId());
                }

                boolean oneInstanceFit = false;
                for(Instance instance : instances){
                    if(instance.getId() == instanceId){
                        oneInstanceFit = true;
                    }
                }

                suitable = oneInstanceFit;
            }

            // Filter for cloud id
            if (suitable && cloudId != null) {
                if(vm.cloud().getId() != cloudId){
                    suitable = false;
                }
            }

            // Add to result
            if (suitable){
                result.add(vm);
            }
        }

        return result;
    }

    @Override public List<Instance> getInstances(Long vm) {
        List<Instance> instances;
        List<Instance> result = new ArrayList<Instance>();

        instances = instanceModelService.getAll();


        for(Instance instance : instances) {
            boolean suitable = true;


            // Filter for application id
            if (vm > 0 && !instance.getVirtualMachine().getId().equals(vm)) {
                suitable = false;
            }

            if (suitable){
                result.add(instance);
            }
        }

        return result;
    }

    @Override public ApplicationComponent getApplicationComponentForInstance(Long appCompId) {
        return applicationComponentModelService.getById(appCompId);
    }

    @Override public String getPublicAddressOfVM(VirtualMachine vm) {
        List<IpAddress> addresses = ipAddressModelService.getAll();

        for(IpAddress ip : addresses){
            /*TODO Not only return ONE, but EACH address */
            if(ip.getVirtualMachine().equals(vm) && ip.getIpType().equals(IpType.PUBLIC)){
                return ip.getIp();
            }
        }

        return null;
    }

    @Override public List<Component> getComponents(
        Long applicationId,
        Long componentId,
        Long instanceId,
        Long cloudId) {
        List<Component> result = new ArrayList<Component>();
        List<Component> components = componentModelService.getAll();
        List<Instance> instances = null;
        List<VirtualMachine> vms = null;

        List<ApplicationComponent> appComps = applicationComponentModelService.getAll();


        for (Component component : components) {
            boolean suitable = false;

            if(applicationId != null) {
                for (ApplicationComponent ac : appComps){
                    if(ac.getComponent().getId().equals(componentId) && ac.getApplication().getId().equals(
                        applicationId)){
                        suitable = true;
                    }
                }
            }



            if(componentId != null){
                if(componentId.equals(component.getId())){
                    suitable = suitable && true;
                }
            }


            if(instanceId != null){
                if(instances==null) instances = instanceModelService.getAll();
                boolean oneFits = false;

                for (Instance instance : instances) {
                    if(isInstanceOf(instance, appComps, component)) {
                        oneFits = true;
                    }
                }

                if(oneFits){
                    suitable = suitable && true;
                } else {
                    suitable = false;
                }
            }


            if(cloudId != null){
                if(instances==null) instances = instanceModelService.getAll();
                if(vms==null) vms = virtualMachineModelService.getAll();
                boolean oneFits = false;

                for (Instance instance : instances) {
                    if(isInstanceOf(instance, vms, cloudId)){
                        if(isInstanceOf(instance, appComps, component)) {
                            oneFits = true;
                        }
                    }
                }

                if(oneFits){
                    suitable = suitable && true;
                } else {
                    suitable = false;
                }
            }


            if(suitable){
                result.add(component);
            }
        }

        return result;
    }

    public boolean isInstanceOf(Instance instance, List<ApplicationComponent> appComps, Component component) {
        boolean result = false;

        Long componentId = component.getId();

        for (ApplicationComponent ac : appComps) {
            Long acId = ac.getId();

            if(instance.getApplicationComponent().getId().equals(acId) &&
                ac.getComponent().getId().equals(componentId)){
                result = true;
            }
        }

        return result;
    }

    public boolean isInstanceOf(Instance instance, List<VirtualMachine> vms, long cloudId) {
        boolean result = false;

        for (VirtualMachine vm : vms) {
            Long vmId = vm.getId();

            if(vm.cloud().getId().equals(cloudId) && instance.getVirtualMachine().getId().equals(
                vmId)){
                result = true;
            }
        }

        return result;
    }

    @Override public String getIpAddress(Long idIpAddress) {
        return ipAddressModelService.getById(idIpAddress).getIp();
    }

    @Override public Long getIdPublicAddressOfVM(VirtualMachine vm) {
        if (vm.publicIpAddress() != null){
            return vm.publicIpAddress().getId();
        }

        return null;
    }

    @Override public List<MonitorInstance> getMonitorInstances(Long monitorId) {
        List<MonitorInstance> result = new ArrayList<>();

        for(MonitorInstance mi : monitorInstanceModelService.getAll()){
            if(mi.getMonitor().getId().equals(monitorId)){
                result.add(mi);
            }
        }

        return result;
    }

    @Override
    public MonitorInstance saveMonitorInstance(Long idMonitor, String apiEndpoint, Long ipAddressId, Long vmId,
        Long componentId) {
        MonitorInstance result = new MonitorInstance(monitorModelService.getById(idMonitor),
            apiEndpoint,
            (ipAddressId == null ? null : ipAddressModelService.getById(ipAddressId)),
            (vmId == null ? null : virtualMachineModelService.getById(vmId)),
            (componentId == null ? null : componentModelService.getById(componentId)));
        monitorInstanceModelService.save(result);
        return result;
    }

    @Override public MonitorInstance getMonitorInstance(Long monitorInstanceId) {
        return monitorInstanceModelService.getById(monitorInstanceId);
    }

    @Override public RawMonitor getRawMonitor(Long monitorId) {
        return rawMonitorModelService.getById(monitorId);
    }

    @Override public ComposedMonitor getComposedMonitor(Long monitorId) {
        return composedMonitorModelService.getById(monitorId);
    }

    @Override public Monitor getMonitor(Long id) {
        return monitorModelService.getById(id);
    }

    @Override public List<Monitor> getMonitors() {
        return monitorModelService.getAll();
    }

    @Override public void removeMonitorInstance(MonitorInstance monitorInstance) {
        monitorInstanceModelService.delete(monitorInstance);
    }
}
