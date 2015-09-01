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
import components.scalability.aggregation.*;
import models.*;
import models.generic.ExternalReference;
import models.scalability.FlowOperator;
import play.Logger;
import play.Play;

import java.util.List;

/**
 * Created by Frank on 20.07.2015.
 */
@Singleton
public class ScalingEngineImpl implements ScalingEngine {
    private static final long NA = -1;
    private final SimpleBlockingQueue<Aggregation> aggregationQueue;
    private final FrontendCommunicator fc;
    private final int AGENT_PORT =
        Play.application().configuration().getInt("colosseum.scalability.visor.port");

    @Inject
    public ScalingEngineImpl(FrontendCommunicator fc,
        @Named("aggregationQueue") SimpleBlockingQueue<Aggregation> aggregationQueue) {
        this.fc = fc;
        this.aggregationQueue = aggregationQueue;
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
                    List<MonitorInstance> monitorInstances = fc.getMonitorInstances(obj.getId());
                    for (MonitorInstance inst : monitorInstances) {
                        //TODO
                        String apiEndpoint = "";
                        fc.saveMonitorInstance(monitor.getId(), apiEndpoint, null, null, null);
                    }
                }
            }
        } else if (monitor.getFlowOperator().equals(FlowOperator.REDUCE)){
            /*

            TODO
            just a workaround, but actually # of instances when reducing
            is always 1

             */
            if(createInstances) {
                //TODO
                String apiEndpoint = "";
                fc.saveMonitorInstance(monitor.getId(), apiEndpoint, null, null, null);
            }
        }

        aggregationQueue.add(new AddAggregation(monitor));

        return monitor;
    }

    @Override public void updateAggregation(ComposedMonitor monitor) {
        if (monitor != null){
            Logger.debug("Update ComposedMonitor: " + monitor.getId());

            aggregationQueue.add(new UpdateAggregation(monitor));
        }

        List<MonitorInstance> monitorInstances = fc.getMonitorInstances(monitor.getId());

        int amountOfNeededInstances = 0;

        if (monitor.getFlowOperator().equals(FlowOperator.MAP)){
            /*

            TODO
            just a workaround, # of instances depend on quantifier

            also store ip of aggregator/tsdb into monitorinstance

            */
            for (Monitor obj : monitor.getMonitors()) {
                List<MonitorInstance> monInstances = fc.getMonitorInstances(obj.getId());
                amountOfNeededInstances += monInstances.size();
            }
        } else if (monitor.getFlowOperator().equals(FlowOperator.REDUCE)){
            /*

            TODO
            just a workaround, but actually # of instances when reducing
            is always 1

             */
            amountOfNeededInstances = 1;
        }

        // Be careful, if a sub-monitor changed, the external
        // references etc. has to be set again for the upper ones
        int i = monitorInstances.size();

        if(amountOfNeededInstances > i){
            int toAdd = amountOfNeededInstances - i;
            for (int j = 0; j < toAdd; ++j) {
                //TODO
                String apiEndpoint = "";
                fc.saveMonitorInstance(monitor.getId(), apiEndpoint, null, null, null);
            }

        } else if (i > amountOfNeededInstances) {
            int toDelete = i - amountOfNeededInstances;
            for (int j = 0; j < toDelete; ++j) {
                fc.removeMonitorInstance(monitorInstances.get(monitorInstances.size() - 1 - j));
            }
        }
    }

    @Override public void removeMonitor(long monitorId) {
        RawMonitor rawMonitor = fc.getRawMonitor(monitorId);
        if(rawMonitor != null) {
            SensorDescription desc = rawMonitor.getSensorDescription();

            List<MonitorInstance> monitorInstances = fc.getMonitorInstances(monitorId);

            for (MonitorInstance monitorInstance : monitorInstances) {
                String ipAddress = fc.getIpAddress(monitorInstance.getIpAddress().getId());

                AgentCommunicator ac = AgentCommunicatorRegistry.getAgentCommunicator("http",
                    ipAddress, AGENT_PORT);

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
            ComposedMonitor composedMonitor = fc.getComposedMonitor(monitorId);
            if (composedMonitor != null){
                Logger.debug("Delete ComposedMonitor: " + composedMonitor.getId());

                aggregationQueue.add(new RemoveAggregation(composedMonitor));
            }

            List<MonitorInstance> monitorInstances = fc.getMonitorInstances(monitorId);

            for (MonitorInstance monitorInstance : monitorInstances) {
                fc.removeMonitorInstance(monitorInstance);
            }
        }

        //TODO now done in controller: remove after integration: fc.deleteMonitorAndItsInstances(monitorId);
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
    public Monitor doMonitor(RawMonitor monitor){
        if (monitor.getSensorDescription().isVmSensor()){
            return doMonitorVms(monitor);
        } else {
            return doMonitorComponents(monitor);
        }
    }

    @Override
    public Monitor doMonitorComponents(RawMonitor monitor){

        /*TODO There arent just lifecycle components */
        List<Component> components = fc.getComponents(
            (monitor.getApplication() == null ? null : monitor.getApplication().getId()),
            (monitor.getComponent() == null ? null : monitor.getComponent().getId()),
            (monitor.getComponentInstance() == null ?
                null :
                monitor.getComponentInstance().getId()),
            (monitor.getCloud() == null ? null : monitor.getCloud().getId()));

        for(Component cid : components){

            List<VirtualMachine> virtualMachines = fc.getVirtualMachines(
                (monitor.getApplication() == null? null : monitor.getApplication().getId()),
                cid.getId(),
                (monitor.getComponentInstance() == null? null : monitor.getComponentInstance().getId()),
                (monitor.getCloud() == null? null : monitor.getCloud().getId()));
            //List<VirtualMachine> virtualMachines = fc.getVirtualMachines(null, comp, null, null);

            addMonitorToVMs(monitor, virtualMachines);
        }


        return monitor;
    }

    @Override
    public Monitor doMonitorVms(RawMonitor monitor){

        List<VirtualMachine> virtualMachines = fc.getVirtualMachines(
            (monitor.getApplication() == null? null : monitor.getApplication().getId()),
            (monitor.getComponent() == null? null : monitor.getComponent().getId()),
            (monitor.getComponentInstance() == null? null : monitor.getComponentInstance().getId()),
            (monitor.getCloud() == null? null : monitor.getCloud().getId()));

        addMonitorToVMs(monitor, virtualMachines);

        return monitor;
    }

    private void addMonitorToVMs(RawMonitor monitor, List<VirtualMachine> virtualMachines){
        for(VirtualMachine vm : virtualMachines) {
            System.out.println("Create VM-Monitor-Instance for: " + fc.getPublicAddressOfVM(vm) + " " + " to this application " + monitor.getApplication());

            /* TODO not magical static values : monitoring agent config (at least port) has to be saved in db */
            AgentCommunicator ac = AgentCommunicatorRegistry.getAgentCommunicator("http",
                fc.getPublicAddressOfVM(vm), AGENT_PORT);

            List<de.uniulm.omi.cloudiator.visor.client.entities.Monitor> monitors = ac.getMonitorWithSameValues(monitor.getSensorDescription().getClassName(), monitor.getSensorDescription().getMetricName(), null);

            if (!monitors.isEmpty()) {
                /*TODO: check for interval: if(hertz(monitor.getInterval())... */
                for(de.uniulm.omi.cloudiator.visor.client.entities.Monitor _monitor : monitors) {
                    ac.removeMonitor(_monitor);
                }
            }

            /* TODO create monitor instance */

            String apiEndpoint = fc.getIpAddress(fc.getIdPublicAddressOfVM(vm));

            MonitorInstance instance = fc.saveMonitorInstance(monitor.getId(), apiEndpoint,
                fc.getIdPublicAddressOfVM(vm), vm.getId(),
                (monitor.getComponent() == null ? null : monitor.getComponent().getId()));

            addMonitorToAgent(ac, instance.getId(), monitor);
        }
    }

    private void addMonitorToAgent(AgentCommunicator ac, Long monitorInstanceId, RawMonitor monitor){
        String sMonitorInstanceId = String.valueOf(monitorInstanceId);

        if(monitor.getSensorDescription().isVmSensor()) {
            ac.addMonitor(sMonitorInstanceId, monitor.getSensorDescription().getClassName(),
                monitor.getSensorDescription().getMetricName(), monitor.getSchedule().getInterval(),
                monitor.getSchedule().getTimeUnit());
        } else {
            String sComponentId = String.valueOf(monitor.getComponent().getId());

            ac.addMonitorForComponent(sMonitorInstanceId, monitor.getSensorDescription().getClassName(),
                monitor.getSensorDescription().getMetricName(), monitor.getSchedule().getInterval(),
                monitor.getSchedule().getTimeUnit(), sComponentId);
        }
    }

    @Override public void updateMonitor(RawMonitor monitor) {
        for(MonitorInstance mi : fc.getMonitorInstances(monitor.getId())){
            AgentCommunicator ac = AgentCommunicatorRegistry.getAgentCommunicator("http",
                mi.getIpAddress().getIp(), AGENT_PORT);

            ac.updateMonitor(mi);
        }
    }

    @Override public void subscribe(Monitor monitor, MonitorSubscription subscription) {
        aggregationQueue.add(new SubscribeAggregation(monitor, subscription));
    }

    @Override public void unsubscribe(Long idSubscription) {
        aggregationQueue.add(new UnsubscribeAggregation(idSubscription));
    }
}
