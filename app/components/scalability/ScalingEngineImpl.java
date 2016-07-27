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
import models.scalability.FlowOperator;
import play.Logger;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Frank on 20.07.2015.
 */
@Singleton public class ScalingEngineImpl implements ScalingEngine {
    private static final long NA = -1;
    private final SimpleBlockingQueue<Aggregation<Monitor>> aggregationQueue;
    private final FrontendCommunicator fc;
    private final int agentPort;
    private final static Logger.ALogger LOGGER = play.Logger.of("colosseum.scalability");

    @Inject public ScalingEngineImpl(FrontendCommunicator fc,
        @Named("aggregationQueue") SimpleBlockingQueue<Aggregation<Monitor>> aggregationQueue,
        int agentPort) {
        this.fc = fc;
        this.aggregationQueue = aggregationQueue;
        this.agentPort = agentPort;
    }


    @Override public Monitor aggregateMonitors(ComposedMonitor monitor, boolean createInstances) {

        Logger.debug("Aggregate ComposedMonitor: " + monitor.getId());

        if (monitor.getFlowOperator().equals(FlowOperator.MAP)) {
            /*

            TODO
            just a workaround, # of instances depend on quantifier

            also store ip of aggregator/tsdb into monitorinstance

            */
            if (createInstances) {
                for (Monitor obj : monitor.getMonitors()) {
                    List<MonitorInstance> monitorInstances = fc.getMonitorInstances(obj.getId());
                    for (MonitorInstance inst : monitorInstances) {
                        //TODO
                        String apiEndpoint = "";
                        fc.saveMonitorInstance(monitor.getId(), apiEndpoint, null, null, null);
                    }
                }
            }
        } else if (monitor.getFlowOperator().equals(FlowOperator.REDUCE)) {
            /*

            TODO
            just a workaround, but actually # of instances when reducing
            is always 1

             */
            if (createInstances) {
                //TODO
                String apiEndpoint = "";
                fc.saveMonitorInstance(monitor.getId(), apiEndpoint, null, null, null);
            }
        }

        aggregationQueue.add(new AddAggregation(monitor));

        return monitor;
    }

    @Override public void updateAggregation(ComposedMonitor monitor) {
        if (monitor != null) {
            Logger.debug("Update ComposedMonitor: " + monitor.getId());

            aggregationQueue.add(new UpdateAggregation(monitor));
        }

        List<MonitorInstance> monitorInstances = fc.getMonitorInstances(monitor.getId());

        int amountOfNeededInstances = 0;

        if (monitor.getFlowOperator().equals(FlowOperator.MAP)) {
            /*

            TODO
            just a workaround, # of instances depend on quantifier

            also store ip of aggregator/tsdb into monitorinstance

            */
            for (Monitor obj : monitor.getMonitors()) {
                List<MonitorInstance> monInstances = fc.getMonitorInstances(obj.getId());
                amountOfNeededInstances += monInstances.size();
            }
        } else if (monitor.getFlowOperator().equals(FlowOperator.REDUCE)) {
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

        if (amountOfNeededInstances > i) {
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
        if (rawMonitor != null) {
            SensorDescription desc = rawMonitor.getSensorDescription();

            List<MonitorInstance> monitorInstances = fc.getMonitorInstances(monitorId);

            for (MonitorInstance monitorInstance : monitorInstances) {
                /*TODO: dangerous, what happens if this vm changes the IP address? */
                String ipAddress = fc.getIpAddress(monitorInstance.getIpAddress().getId());

                AgentCommunicator ac =
                    AgentCommunicatorRegistry.getAgentCommunicator("http", ipAddress, agentPort);

                if(desc.isPush()) {
                    List<de.uniulm.omi.cloudiator.visor.client.entities.PushMonitor> monitors =
                            ac.getPushMonitorWithSameValues(desc.getMetricName(), null /*TODO*/);

                    if (!monitors.isEmpty()) {
                        for (de.uniulm.omi.cloudiator.visor.client.entities.PushMonitor agentMonitor : monitors) {
                            LOGGER.info("Delete Raw Push Monitor: " + monitorId);

                            ac.removePushMonitor(agentMonitor);
                        }
                    }

                    fc.removeMonitorInstance(monitorInstance);
                } else {
                    List<de.uniulm.omi.cloudiator.visor.client.entities.SensorMonitor> monitors =
                            ac.getSensorMonitorWithSameValues(desc.getClassName(), desc.getMetricName(),
                                    null /*TODO*/);

                    if (!monitors.isEmpty()) {
                        /*TODO: check for interval: if(hertz(monitor.getInterval())... */
                        for (de.uniulm.omi.cloudiator.visor.client.entities.SensorMonitor agentMonitor : monitors) {
                            LOGGER.info("Delete Raw Sensor Monitor: " + monitorId);

                            ac.removeSensorMonitor(agentMonitor);
                        }
                    }

                    fc.removeMonitorInstance(monitorInstance);
                }
            }
        } else {
            ComposedMonitor composedMonitor = fc.getComposedMonitor(monitorId);
            if (composedMonitor != null) {
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
        this.addExternalIdToMonitor(monitorId, "rnd" + UUID.randomUUID().toString(), externalId);
    }

    @Override
    public void addExternalIdToMonitor(Long monitorId, String externalKey, String externalId) {
        fc.getMonitor(monitorId).externalReferences().put(externalKey, externalId);
    }

    @Override public void addExternalId(Long monitorInstanceId, String externalId) {

        this.addExternalId(monitorInstanceId, "rnd" + UUID.randomUUID().toString(), externalId);
    }

    @Override
    public void addExternalId(Long monitorInstanceId, String externalKey, String externalId) {
        fc.getMonitorInstance(monitorInstanceId).externalReferences().put(externalKey, externalId);
    }

    @Override public void addExternalId(Long monitorId, String externalId, Long virtualMachine) {
        this.addExternalId(monitorId, "rnd" + UUID.randomUUID().toString(), externalId,
            virtualMachine);

    }

    @Override public void addExternalId(Long monitorId, String externalKey, String externalId,
        Long virtualMachine) {
        List<MonitorInstance> monitorInstances = fc.getMonitorInstances(monitorId);


        for (MonitorInstance mi : monitorInstances) {
            if (mi.getVirtualMachine().getId().equals(virtualMachine))
                mi.externalReferences().put(externalKey, externalId);
        }
    }


    @Override public void addExternalId(Long monitorId, String externalId, Long virtualMachine,
        Long componentId) {
        this.addExternalId(monitorId, "rnd" + UUID.randomUUID().toString(), externalId,
            virtualMachine, componentId);
    }

    @Override public void addExternalId(Long monitorId, String externalKey, String externalId,
        Long virtualMachine, Long componentId) {
        List<MonitorInstance> monitorInstances = fc.getMonitorInstances(monitorId);


        for (MonitorInstance mi : monitorInstances) {
            if (mi.getVirtualMachine().getId().equals(virtualMachine) && mi.getComponent().getId()
                .equals(componentId))
                mi.externalReferences().put(externalKey, externalId);

        }
    }

    @Override public Monitor doMonitor(RawMonitor monitor) {
        if (monitor.getSensorDescription().isVmSensor()) {
            return doMonitorVms(monitor);
        } else {
            return doMonitorComponents(monitor);
        }
    }

    @Override public Monitor doMonitorComponents(RawMonitor monitor) {

        /*TODO There arent just lifecycle components */
        List<Component> components = fc.getComponents(
            (monitor.getApplication() == null ? null : monitor.getApplication().getId()),
            (monitor.getComponent() == null ? null : monitor.getComponent().getId()),
            (monitor.getComponentInstance() == null ?
                null :
                monitor.getComponentInstance().getId()),
            (monitor.getCloud() == null ? null : monitor.getCloud().getId()));

        for (Component cid : components) {

            List<VirtualMachine> virtualMachines = fc.getVirtualMachines(
                (monitor.getApplication() == null ? null : monitor.getApplication().getId()),
                cid.getId(), (monitor.getComponentInstance() == null ?
                    null :
                    monitor.getComponentInstance().getId()),
                (monitor.getCloud() == null ? null : monitor.getCloud().getId()));
            //List<VirtualMachine> virtualMachines = fc.getVirtualMachines(null, comp, null, null);

            addMonitorToVMs(monitor, virtualMachines);
        }


        return monitor;
    }

    @Override public Monitor doMonitorVms(RawMonitor monitor) {

        List<VirtualMachine> virtualMachines = fc.getVirtualMachines(
            (monitor.getApplication() == null ? null : monitor.getApplication().getId()),
            (monitor.getComponent() == null ? null : monitor.getComponent().getId()),
            (monitor.getComponentInstance() == null ?
                null :
                monitor.getComponentInstance().getId()),
            (monitor.getCloud() == null ? null : monitor.getCloud().getId()));

        addMonitorToVMs(monitor, virtualMachines);

        return monitor;
    }

    private void addMonitorToVMs(RawMonitor monitor, List<VirtualMachine> virtualMachines) {
        for (VirtualMachine vm : virtualMachines) {
            LOGGER.info("Create VM-Monitor-Instance for: " + fc.getPublicAddressOfVM(vm) + " "
                + " to this application " + monitor.getApplication());

            /* TODO not magical static values : monitoring agent config (at least port) has to be saved in db */
            AgentCommunicator ac = AgentCommunicatorRegistry
                .getAgentCommunicator("http", fc.getPublicAddressOfVM(vm), agentPort);

            if(monitor.getSensorDescription().isPush()){
                List<de.uniulm.omi.cloudiator.visor.client.entities.PushMonitor> monitors =
                        ac.getPushMonitorWithSameValues(
                                monitor.getSensorDescription().getMetricName(), null);

                if (!monitors.isEmpty()) {
                    for (de.uniulm.omi.cloudiator.visor.client.entities.PushMonitor _monitor : monitors) {
                        ac.removePushMonitor(_monitor);
                    }
                }

                /* TODO create monitor instance not by frontend communicator... */
                String apiEndpoint = fc.getIpAddress(fc.getIdPublicAddressOfVM(vm));

                MonitorInstance instance =
                        fc.saveMonitorInstance(monitor.getId(), apiEndpoint, fc.getIdPublicAddressOfVM(vm),
                                vm.getId(),
                                (monitor.getComponent() == null ? null : monitor.getComponent().getId()));

                addMonitorToAgent(ac, instance, monitor);
            } else {
                List<de.uniulm.omi.cloudiator.visor.client.entities.SensorMonitor> monitors =
                        ac.getSensorMonitorWithSameValues(monitor.getSensorDescription().getClassName(),
                                monitor.getSensorDescription().getMetricName(), null);

                if (!monitors.isEmpty()) {
                    /*TODO: check for interval: if(hertz(monitor.getInterval())... */
                    for (de.uniulm.omi.cloudiator.visor.client.entities.SensorMonitor _monitor : monitors) {
                        ac.removeSensorMonitor(_monitor);
                    }
                }

                /* TODO create monitor instance not by frontend communicator... */
                String apiEndpoint = fc.getIpAddress(fc.getIdPublicAddressOfVM(vm));

                MonitorInstance instance =
                        fc.saveMonitorInstance(monitor.getId(), apiEndpoint, fc.getIdPublicAddressOfVM(vm),
                                vm.getId(),
                                (monitor.getComponent() == null ? null : monitor.getComponent().getId()));

                addMonitorToAgent(ac, instance, monitor);
            }
        }
    }

    private void addMonitorToAgent(AgentCommunicator ac, MonitorInstance instance,
        RawMonitor monitor) {
        String sMonitorInstanceId = String.valueOf(instance.getId());
        Map<String, String> configs = null;
        if (monitor.getSensorConfigurations().isPresent()) {
            final SensorConfigurations sensorConfigurations =
                monitor.getSensorConfigurations().get();
            configs = sensorConfigurations.configs();
        }


        if(monitor.getSensorDescription().isPush()){
            if (monitor.getSensorDescription().isVmSensor()) {
                Integer port = ac.addPushMonitor(sMonitorInstanceId,
                        monitor.getSensorDescription().getMetricName());

                instance.setPort(port);
                fc.saveMonitorInstance(instance);
            } else {
                String sComponentId = String.valueOf(monitor.getComponent().getId());

                Integer port = ac.addPushMonitorForComponent(sMonitorInstanceId,
                        monitor.getSensorDescription().getMetricName(), sComponentId);

                instance.setPort(port);
                fc.saveMonitorInstance(instance);
            }
        } else {
            if (monitor.getSensorDescription().isVmSensor()) {
                ac.addSensorMonitor(sMonitorInstanceId, monitor.getSensorDescription().getClassName(),
                        monitor.getSensorDescription().getMetricName(), monitor.getSchedule().getInterval(),
                        monitor.getSchedule().getTimeUnit(), configs);
            } else {
                String sComponentId = String.valueOf(monitor.getComponent().getId());

                ac.addSensorMonitorForComponent(sMonitorInstanceId,
                        monitor.getSensorDescription().getClassName(),
                        monitor.getSensorDescription().getMetricName(), monitor.getSchedule().getInterval(),
                        monitor.getSchedule().getTimeUnit(), sComponentId, configs);
            }
        }
    }

    @Override public void updateMonitor(RawMonitor monitor) {
        for (MonitorInstance mi : fc.getMonitorInstances(monitor.getId())) {
            AgentCommunicator ac = AgentCommunicatorRegistry
                .getAgentCommunicator("http", mi.getIpAddress().getIp(), agentPort);

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
