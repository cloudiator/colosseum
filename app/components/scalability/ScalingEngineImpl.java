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
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import components.execution.SimpleBlockingQueue;
import models.*;
import models.generic.ExternalReference;
import models.scalability.FlowOperator;
import models.scalability.FormulaOperator;
import play.Logger;

import java.util.List;

/**
 * Created by Frank on 20.07.2015.
 */
@Singleton
public class ScalingEngineImpl implements ScalingEngine {
    private static final long NA = -1;
    private final SimpleBlockingQueue<Aggregation> aggregationQueue;
    private final FrontendCommunicator fc;
    private final DatabaseAccess db;
    private final int agentPort;

    @Inject
    public ScalingEngineImpl(FrontendCommunicator fc,
        @Named("aggregationQueue") SimpleBlockingQueue<Aggregation> aggregationQueue
        /*, int agentPort*/) {
        this.db = fc;
        this.fc = fc;
        /*TODO make this dynamic: */
        //this.agentPort = agentPort;
        this.agentPort = 31415;
        this.aggregationQueue = aggregationQueue;
    }

    @Override public Monitor doMonitorComponents(Application applicationId, Schedule schedule,
        SensorDescription desc) {
        return _doMonitorComponents(applicationId.getId(), NA, NA, NA, schedule, desc);
    }

    @Override public Monitor doMonitorComponents(Application applicationId, Component component,
        Schedule schedule, SensorDescription desc) {
        return null;
    }

    @Override public Monitor doMonitorComponents(Application application, Component component,
        Instance compInstance, Schedule schedule, SensorDescription desc) {
        return null;
    }

    @Override public Monitor doMonitorComponents(Application application, Component componentId,
        Cloud cloudId, Schedule schedule, SensorDescription desc) {
        return null;
    }

    @Override
    public Monitor doMonitorComponents(Application application, Cloud cloud, Schedule schedule,
        SensorDescription desc) {
        return null;
    }

    @Override
    public Monitor doMonitorVms(Application appId, Schedule schedule, SensorDescription desc) {
        return null;
    }

    @Override public Monitor doMonitorVms(Application appId, Cloud cid, Schedule schedule,
        SensorDescription desc) {
        return null;
    }

    @Override public Monitor doMonitorVms(Application appId, Component cid, Schedule schedule,
        SensorDescription desc) {
        return null;
    }

    @Override
    public Monitor doMonitorVms(Application appId, Component cid, Cloud cloudId, Schedule schedule,
        SensorDescription desc) {
        return null;
    }

    @Override public Monitor mapAggregatedMonitors(FormulaQuantifier quantifier, Schedule schedule,
        Window window, FormulaOperator formulaOperator, List<Monitor> monitors) {
        return null;
    }

    @Override
    public Monitor reduceAggregatedMonitors(FormulaQuantifier quantifier, Schedule schedule,
        Window window, FormulaOperator formulaOperator, List<Monitor> monitors) {
        return null;
    }

    @Override
    public Monitor aggregateMonitors(ComposedMonitor monitor, boolean createInstances) {

        Logger.debug("Aggregate ComposedMonitor: " + monitor.getId());

        if (monitor.getFlowOperator().equals(FlowOperator.MAP)){
            /*

            TODO
            just a workaround, # of instances depend on quantifier

            also store ip of aggregator/tsdb into monitorinstance

            */
            if(createInstances) {
                for (Monitor obj : monitor.getMonitors()) {
                    List<MonitorInstance> monitorInstances = db.getMonitorInstances(obj.getId());
                    for (MonitorInstance inst : monitorInstances) {
                        db.saveMonitorInstance(monitor.getId(), null, null, null);
                    }
                }
            }


            aggregationQueue.add(new AddAggregation(Converter.convert(monitor)));

            return monitor;
        } else if (monitor.getFlowOperator().equals(FlowOperator.REDUCE)){
            /*

            TODO
            just a workaround, but actually # of instances when reducing
            is always 1

             */
            if(createInstances) {
                db.saveMonitorInstance(monitor.getId(), null, null, null);
            }

            aggregationQueue.add(new AddAggregation(Converter.convert(monitor)));

            return monitor;
        }

        return null;
    }

    @Override public void removeMonitor(long monitorId) {
        RawMonitor rawMonitor = db.getRawMonitor(monitorId);
        if(rawMonitor != null) {
            SensorDescription desc = rawMonitor.getSensorDescription();

            List<MonitorInstance> monitorInstances = db.getMonitorInstances(monitorId);

            for (MonitorInstance monitorInstance : monitorInstances) {
                String ipAddress = fc.getIpAddress(monitorInstance.getIpAddress().getId());

                AgentCommunicator ac = AgentCommunicator.getCommunicator("http", ipAddress, agentPort);

                List<de.uniulm.omi.cloudiator.visor.client.entities.Monitor> monitors = ac.getMonitorWithSameValues(desc.getClassName(), desc.getMetricName(), null /*TODO*/);

                if (!monitors.isEmpty()) {
                /*TODO: check for interval: if(hertz(monitor.getInterval())... */
                    for (de.uniulm.omi.cloudiator.visor.client.entities.Monitor agentMonitor : monitors) {
                        System.out.println("Delete Raw Monitor: " + monitorId);

                        ac.removeMonitor(agentMonitor);
                    }
                }

                fc.removeMonitorInstance(monitorInstance);
            }
        } else {
            ComposedMonitor composedMonitor = db.getComposedMonitor(monitorId);
            if (composedMonitor != null){
                Logger.debug("Delete ComposedMonitor: " + composedMonitor.getId());

                aggregationQueue.add(new RemoveAggregation(Converter.convert(composedMonitor)));
            }

            List<MonitorInstance> monitorInstances = db.getMonitorInstances(monitorId);

            for (MonitorInstance monitorInstance : monitorInstances) {
                fc.removeMonitorInstance(monitorInstance);
            }
        }

        //TODO now done in controller: remove after integration: db.deleteMonitorAndItsInstances(monitorId);
    }

    @Override public void addExternalIdToMonitor(Long monitorId, String externalId) {
        fc.getMonitor(monitorId).getExternalReferences().add(new ExternalReference(externalId));
    }

    @Override public void addExternalId(Long monitorInstanceId, String externalId) {
        fc.getMonitorInstance(monitorInstanceId).getExternalReferences().add(new ExternalReference(externalId));
    }

    @Override public void addExternalId(Long monitorId, String externalId, Long virtualMachine) {
        List<MonitorInstance> monitorInstances = fc.getMonitorInstances(monitorId);

        for(MonitorInstance mi : monitorInstances){
            if(mi.getVirtualMachine().getId().equals(virtualMachine))
                mi.getExternalReferences().add(new ExternalReference(externalId));
        }
    }

    @Override public void addExternalId(Long monitorId, String externalId, Long virtualMachine,
        Long componentId) {
        List<MonitorInstance> monitorInstances = fc.getMonitorInstances(monitorId);

        for(MonitorInstance mi : monitorInstances){
            if(mi.getVirtualMachine().getId().equals(virtualMachine) &&
                mi.getComponent().getId().equals(componentId))
            mi.getExternalReferences().add(new ExternalReference(externalId));
        }
    }

    @Override
    public Monitor _doMonitorVms(long applicationId, long componentId, long instanceId, long cloudId, Schedule schedule, SensorDescription sensorDescription){

        /* TODO save sensorConfiguration to DB - maybe done somewhere else? */
        SensorDescription desc = db.saveSensorDescription(sensorDescription.getClassName(), sensorDescription.getMetricName(), sensorDescription.isVmSensor());
        // not needed anymore SensorConfiguration config = db.saveSensorConfiguration(desc, schedule.getInterval(), schedule.getTimeUnit());
        Schedule dbSchedule = db.saveSchedule(schedule.getInterval(), schedule.getTimeUnit());

        /* TODO create monitor and save to DB */
        Monitor resultMonitor = db.saveRawMonitor(applicationId, componentId, instanceId, cloudId, dbSchedule, desc);
        //Monitor resultMonitor = new Monitor(0 /**false id**/, applicationId, componentId, instanceId, cloudId, sensorDescription);


        List<VirtualMachine> virtualMachines = fc.getVirtualMachines(applicationId, componentId, instanceId, cloudId);

        for(VirtualMachine vm : virtualMachines) {
            System.out.println("Create VM-Monitor-Instance for: " + fc.getPublicAddressOfVM(vm) + " " + " to this application " + applicationId);

            /* TODO not magical static values : monitoring agent config (at least port) has to be saved in db */
            AgentCommunicator ac = AgentCommunicator.getCommunicator("http", fc.getPublicAddressOfVM(vm), agentPort);

            List<de.uniulm.omi.cloudiator.visor.client.entities.Monitor> monitors = ac.getMonitorWithSameValues(sensorDescription.getClassName(), sensorDescription.getMetricName(), null);

            if (!monitors.isEmpty()) {
                /*TODO: check for interval: if(hertz(monitor.getInterval())... */
                for(de.uniulm.omi.cloudiator.visor.client.entities.Monitor monitor : monitors) {
                    ac.removeMonitor(monitor);
                }

            }




            /* TODO create monitor instance */

            MonitorInstance instance = db.saveMonitorInstance(resultMonitor.getId(), fc.getIdPublicAddressOfVM(vm), vm.getId(), componentId);


            ac.addMonitor(String.valueOf(instance.getId()), sensorDescription.getClassName(), sensorDescription.getMetricName(), schedule.getInterval(), schedule.getTimeUnit());
        }

        return resultMonitor;
    }

    @Override
    public Monitor _doMonitorComponents(long applicationId, long componentId, long instanceId, long cloudId, Schedule schedule, SensorDescription sensorDescription){

        /* TODO save sensorConfiguration to DB - maybe done somewhere else? */
        SensorDescription desc = db.saveSensorDescription(sensorDescription.getClassName(), sensorDescription.getMetricName(), sensorDescription.isVmSensor());
        // not needed anymore: SensorConfiguration config = db.saveSensorConfiguration(desc, schedule.getInterval(), schedule.getTimeUnit());
        Schedule dbSchedule = db.saveSchedule(schedule.getInterval(), schedule.getTimeUnit());

        /* TODO create monitor and save to DB */
        Monitor resultMonitor = db.saveRawMonitor(applicationId, componentId, instanceId, cloudId, dbSchedule, desc);
        //Monitor resultMonitor = new Monitor(0 /**false id**/, applicationId, componentId, instanceId, cloudId, sensorDescription);



        /*TODO There arent just lifecycle components */
        List<LifecycleComponent> components = fc.getComponents(applicationId, componentId, instanceId, cloudId);

        for(LifecycleComponent cid : components){

            List<VirtualMachine> virtualMachines = fc.getVirtualMachines(applicationId, cid.getId(), instanceId, cloudId);
            //List<VirtualMachine> virtualMachines = fc.getVirtualMachines(null, comp, null, null);

            for(VirtualMachine vm : virtualMachines) {
                System.out.println("Create Component-Monitor-Instance for: " + fc.getPublicAddressOfVM(vm) + " " + " to this application " + applicationId);

                /* TODO not static values : monitoring agent config has to be saved in db */
                AgentCommunicator ac = AgentCommunicator.getCommunicator("http", fc.getPublicAddressOfVM(vm), agentPort);

                List<de.uniulm.omi.cloudiator.visor.client.entities.Monitor> monitors = ac.getMonitorWithSameValues(sensorDescription.getClassName(), sensorDescription.getMetricName(), String.valueOf(cid.getId()));

                if (!monitors.isEmpty()) {
                    /*TODO: check for interval: if(hertz(monitor.getInterval())... */
                    for(de.uniulm.omi.cloudiator.visor.client.entities.Monitor monitor : monitors) {
                        ac.removeMonitor(monitor);
                    }

                }


                /* TODO create monitor instance */

                MonitorInstance instance = db.saveMonitorInstance(resultMonitor.getId(), fc.getIdPublicAddressOfVM(vm), vm.getId(), componentId);



                ac.addMonitorForComponent(String.valueOf(instance.getId()), sensorDescription.getClassName(), sensorDescription.getMetricName(), schedule.getInterval(), schedule.getTimeUnit(), String.valueOf(cid.getId()));

            }
        }


        return resultMonitor;
    }

    @Override
    public Monitor _doMonitorVms(long applicationId, long componentId, long instanceId, long cloudId, RawMonitor monitor){

        List<VirtualMachine> virtualMachines = fc.getVirtualMachines(applicationId, componentId, instanceId, cloudId);

        for(VirtualMachine vm : virtualMachines) {
            System.out.println("Create VM-Monitor-Instance for: " + fc.getPublicAddressOfVM(vm) + " " + " to this application " + applicationId);

            /* TODO not magical static values : monitoring agent config (at least port) has to be saved in db */
            AgentCommunicator ac = AgentCommunicator.getCommunicator("http", fc.getPublicAddressOfVM(vm), agentPort);

            List<de.uniulm.omi.cloudiator.visor.client.entities.Monitor> monitors = ac.getMonitorWithSameValues(monitor.getSensorDescription().getClassName(), monitor.getSensorDescription().getMetricName(), null);

            if (!monitors.isEmpty()) {
                /*TODO: check for interval: if(hertz(monitor.getInterval())... */
                for(de.uniulm.omi.cloudiator.visor.client.entities.Monitor _monitor : monitors) {
                    ac.removeMonitor(_monitor);
                }

            }




            /* TODO create monitor instance */

            MonitorInstance instance = db.saveMonitorInstance(monitor.getId(), fc.getIdPublicAddressOfVM(vm), vm.getId(), componentId);


            ac.addMonitor(String.valueOf(instance.getId()), monitor.getSensorDescription().getClassName(), monitor.getSensorDescription().getMetricName(), monitor.getSchedule().getInterval(), monitor.getSchedule().getTimeUnit());
        }

        return monitor;
    }

    @Override public void updateMonitor(RawMonitor monitor) {
        for(MonitorInstance mi : fc.getMonitorInstances(monitor.getId())){
            AgentCommunicator ac = AgentCommunicator.getCommunicator("http", mi.getIpAddress().getIp(), agentPort);

            ac.updateMonitor(mi);
        }
    }

    @Override public void subscribe(Monitor monitor, MonitorSubscription subscription) {
        aggregationQueue.add(new SubscribeAggregation(Converter.convert(monitor), subscription));
    }

    @Override public void unsubscribe(Long idSubscription) {
        aggregationQueue.add(new UnsubscribeAggregation(idSubscription));
    }
}
