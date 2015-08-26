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

    @Override public List<VirtualMachine> getVirtualMachines(long applicationId, long componentId,
        long instanceId, long cloudId) {
        List<VirtualMachine> vms;
        List<VirtualMachine> result = new ArrayList<VirtualMachine>();

        vms = virtualMachineModelService.getAll();

        for(VirtualMachine vm : vms) {
            boolean suitable = true;
            List<Instance> instances = null;
            List<ApplicationComponent> appComps = null;


            // Filter for application id
            if (Check.idNotNull(applicationId)) {
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
            if (suitable && Check.idNotNull(componentId)) {
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
            if (suitable && Check.idNotNull(instanceId)) {
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
            if (suitable && Check.idNotNull(cloudId)) {
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

    @Override public List<Instance> getInstances(long vm) {
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

    @Override public ApplicationComponent getApplicationComponentForInstance(long appCompId) {
        return applicationComponentModelService.getById(appCompId);
    }

    @Override public long getIdFromLink(String link) {
        return 0;
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

    @Override public List<LifecycleComponent> getComponents(long applicationId, long componentId,
        long instanceId, long cloudId) {
        return null;
    }

    @Override public boolean isInstanceOf(Instance instance, List<ApplicationComponent> appComps,
        LifecycleComponent component) {
        return false;
    }

    @Override
    public boolean isInstanceOf(Instance instance, List<VirtualMachine> vms, long cloudId) {
        return false;
    }

    @Override public String getIpAddress(long idIpAddress) {
        return ipAddressModelService.getById(idIpAddress).getIp();
    }

    @Override public long getIdPublicAddressOfVM(VirtualMachine vm) {
        if (vm.publicIpAddress() != null){
            return vm.publicIpAddress().getId();
        }

        return 0;
    }

    @Override public Long getVirtualMachineToIP(String ipAddress) {
        return null;
    }

    @Override public Long getApplicationIdByName(String name) {
        return null;
    }

    @Override public Long getComponentIdByName(String name) {
        Long result = null;

        /* TODO NOT ONLY LC COMPONENT */
        for(Component component : componentModelService.getAll()){
            if (component instanceof LifecycleComponent && component.getName().equals(name)){
                result = component.getId();
                break;
            }
        }

        return result;
    }

    @Override public List<MonitorInstance> getMonitorInstances(long idMonitor) {
        return null;
    }

    @Override public List<Long> getMonitorInstanceIDs(long idMonitor) {
        List<MonitorInstance> monitorInstances = getMonitorInstances(idMonitor);
        List<Long> result = new ArrayList<Long>();

        for(MonitorInstance monitorInstance : monitorInstances){
            result.add(monitorInstance.getId());
        }

        return result;
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
        return null;
    }

    @Override public Collection<MonitorInstance> getMonitorInstances() {
        return null;
    }

    @Override
    public RawMonitor saveRawMonitor(Long applicationId, Long componentId, Long compInstanceId,
        Long cloudId, Schedule schedule, SensorDescription config) {
        return null;
    }

    @Override public RawMonitor getRawMonitor(Long monitorId) {
        return rawMonitorModelService.getById(monitorId);
    }

    @Override public Collection<RawMonitor> getRawMonitors() {
        return null;
    }

    @Override public ConstantMonitor saveConstantMonitor(Double val) {
        return null;
    }

    @Override public ConstantMonitor getConstantMonitor(Long monitorId) {
        return null;
    }

    @Override public Collection<ConstantMonitor> getConstantMonitors() {
        return null;
    }

    @Override
    public ComposedMonitor saveComposedMonitor(FlowOperator flow, FormulaQuantifier quantifier,
        Schedule schedule, Window window, FormulaOperator formulaOperator, List<Monitor> monitors) {
        return null;
    }

    @Override public ComposedMonitor getComposedMonitor(Long monitorId) {
        return composedMonitorModelService.getById(monitorId);
    }

    @Override public List<ComposedMonitor> getComposedMonitors() {
        return null;
    }

    @Override public SensorDescription saveSensorDescription(String _className, String _metricName,
        boolean _isVmSensor) {
        SensorDescription result = new SensorDescription(_className, _metricName, _isVmSensor);
        sensorDescriptionModelService.save(result);
        return result;
    }

    @Override public SensorDescription getSensorDescription(SensorDescription sensorDescription) {
        return null;
    }

    @Override public List<SensorDescription> getSensorDescriptions() {
        return null;
    }

    @Override public TimeWindow saveTimeWindow(Long interval, TimeUnit timeUnit) {
        return null;
    }

    @Override public TimeWindow getTimeWindow(Long windowId) {
        return null;
    }

    @Override public List<TimeWindow> getTimeWindows() {
        return null;
    }

    @Override public MeasurementWindow saveMeasurementWindow(Long measurements) {
        return null;
    }

    @Override public MeasurementWindow getMeasurementWindow(Long windowId) {
        return null;
    }

    @Override public List<MeasurementWindow> getMeasurementWindows() {
        return null;
    }

    @Override public Schedule saveSchedule(Long interval, TimeUnit timeUnit) {
        Schedule result = new Schedule(interval, timeUnit);
        scheduleModelService.save(result);
        return result;
    }

    @Override public Schedule getSchedule(Long scheduleId) {
        return null;
    }

    @Override public List<Schedule> getSchedules() {
        return null;
    }

    @Override
    public ComponentHorizontalOutScalingAction saveComponentHorizontalOutScalingAction(Long amount,
        Long min, Long max, Long count, Long component) {
        return null;
    }

    @Override
    public ComponentHorizontalOutScalingAction getComponentHorizontalOutScalingAction(Long id) {
        return null;
    }

    @Override
    public List<ComponentHorizontalOutScalingAction> getComponentHorizontalOutScalingAction() {
        return null;
    }

    @Override public Monitor getMonitor(Long id) {
        return null;
    }

    @Override public void deleteMonitorAndItsInstances(Long id) {
        //TODO not needed anymore? REMOVE after INTEGRATION!
    }

    @Override public void setExternalIDToMonitor(Long monitorId, String externalId) {

    }

    @Override
    public void setExternalIDToMonitorInstance(Long monitorInstanceId, String externalId) {

    }

    @Override public void setExternalIDToMonitorInstance(Long monitorInstanceId, String externalId,
        Long virtualMachine) {

    }

    @Override public void setExternalIDToMonitorInstance(Long monitorInstanceId, String externalId,
        Long virtualMachine, Long componentId) {

    }

    @Override public Schedule getSchedule(Long interval, TimeUnit timeUnit) throws Exception {
        return null;
    }

    @Override public SensorDescription getSensorDescription(String _className, String _metricName,
        boolean _isVmSensor) {
        return null;
    }

    @Override public TimeWindow getTimeWindow(Long interval, TimeUnit timeUnit) {
        return null;
    }

    @Override public List<Monitor> getMonitors() {
        return null;
    }

    @Override public Schedule getLowestSchedule(List<Monitor> monitors) throws Exception {
        return null;
    }

    @Override public TimeWindow getSmallestTimeWindow(List<Monitor> monitors) throws Exception {
        return null;
    }

    @Override public ComposedMonitor getComposedMonitorByExternalId(String externalId) {
        return null;
    }

    @Override public void addScalingAction(ComposedMonitor monitor, ScalingAction action) {

    }

    @Override public void removeMonitorInstance(MonitorInstance monitorInstance) {
        monitorInstanceModelService.delete(monitorInstance);
    }
}
