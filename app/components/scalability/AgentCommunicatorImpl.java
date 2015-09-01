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

import de.uniulm.omi.cloudiator.visor.client.ClientBuilder;
import de.uniulm.omi.cloudiator.visor.client.ClientController;
import de.uniulm.omi.cloudiator.visor.client.entities.Context;
import de.uniulm.omi.cloudiator.visor.client.entities.Interval;
import de.uniulm.omi.cloudiator.visor.client.entities.Monitor;
import models.MonitorInstance;
import models.RawMonitor;
import models.generic.ExternalReference;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Frank on 01.09.2015.
 */
public class AgentCommunicatorImpl implements AgentCommunicator {
    private final String protocol;
    private final String ip;
    private final int port;
    private final ClientController<Monitor> controller;

    public AgentCommunicatorImpl(String protocol, String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.protocol = protocol;

        //get the controller for the cloud entity
        this.controller =
            ClientBuilder.getNew()
                // the base url
                .url(protocol + "://" + ip + ":" + port)
                    // the entity to get the controller for.
                .build(Monitor.class);

    }

    /*
    TODO This probably won't work...
     */
    @Override public void removeMonitor(String className, String metricName, long interval, TimeUnit unit){
        Monitor monitor = Monitor.builder().sensorClassName(className).metricName(metricName).interval(interval, unit).build();

        //remove a Monitor
        controller.delete(monitor);
    }

    @Override public void addMonitor(String idMonitorInstance, String className, String metricName, long interval, TimeUnit unit){
        Monitor monitor = Monitor.builder().sensorClassName(className).metricName(
            metricName).interval(interval, unit).addContext("monitorinstance", idMonitorInstance).build();

        //create a new Monitor
        monitor = controller.create(monitor);
    }

    /*
    TODO This probably won't work...
     */
    @Override public void removeMonitorForComponent(String className, String metricName, long interval, TimeUnit unit, String componentId){
        Monitor monitor = Monitor.builder().sensorClassName(className).metricName(metricName).interval(interval, unit).addContext("component", componentId).build();

        //remove a Monitor
        controller.delete(monitor);
    }

    @Override public void removeMonitor(Monitor monitor) {
        //remove a Monitor
        controller.delete(monitor);
    }


    @Override public void addMonitorForComponent(String idMonitorInstance, String className, String metricName, long interval, TimeUnit unit, String componentId){
        Monitor monitor = Monitor.builder().sensorClassName(className).metricName(
            metricName).interval(interval, unit).addContext("component", componentId).addContext("monitorinstance", idMonitorInstance).build();

        //create a new Monitor
        controller.create(monitor);
    }

    @Override public List<Monitor> getMonitorWithSameValues(String className, String metricName, String componentName) {
        List<Monitor> monitors = controller.getList();
        List<Monitor> result = new ArrayList<Monitor>();

        for(Monitor monitor : monitors){
            if (monitor.getSensorClassName().equals(className) &&
                monitor.getMetricName().equals(metricName) &&
                checkForComponentContext(componentName, monitor.getContexts())){
                result.add(monitor);
            }
        }

        return result;
        //return new ArrayList<Monitor>();
    }

    @Override public void updateMonitor(MonitorInstance mi) {
        List<Monitor> mons = controller.getList();

        for(Monitor mon : mons){
            if (hasSameContext(mon, "monitorinstance", mi.getId().toString())){
                controller.update(copyValueFromMonitorInstance(mon, mi));
            }
        }
    }

    @Override public boolean hasSameContext(Monitor mon, String contextKey, String contextValue){
        for(Context con : mon.getContexts()){
            if(con.getKey().equals(contextKey) && con.getValue().equals(contextValue)){
                return true;
            }
        }

        return false;
    }

    @Override public Monitor copyValueFromMonitorInstance(Monitor m, MonitorInstance mi){
        for(Context c : m.getContexts()){
            if(c.getKey().equals("monitorinstance")){
                c.setValue(mi.getId().toString());
            }
        }

        for(ExternalReference er : mi.getExternalReferences()){
            m.getContexts().add(new Context("er_" + er.getId(), er.getReference()));
        }

        m.setSensorClassName(((RawMonitor)mi.getMonitor()).getSensorDescription().getClassName());
        m.setMetricName(((RawMonitor)mi.getMonitor()).getSensorDescription().getMetricName());
        m.setInterval(new Interval(((RawMonitor)mi.getMonitor()).getSchedule().getInterval(), ((RawMonitor)mi.getMonitor()).getSchedule().getTimeUnit().toString()));
        return m;
    }

    @Override public int getPort() {
        return port;
    }

    @Override public String getIp() {
        return ip;
    }

    @Override public String getProtocol() {
        return protocol;
    }

    private boolean checkForComponentContext(String componentName, List<Context> contexts) {
        boolean isComponent = false;

        if (componentName == null || componentName.equals("")){
            isComponent = true;
        } else {
            for(Context context : contexts){
                if(context.getKey().equals("component") && context.getValue().equals(componentName)){
                    isComponent = true;
                }
            }
        }

        return isComponent;
    }
}
