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

package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;

import de.uniulm.omi.cloudiator.persistance.entities.Component;
import de.uniulm.omi.cloudiator.persistance.entities.Monitor;
import de.uniulm.omi.cloudiator.persistance.entities.VirtualMachine;
import de.uniulm.omi.cloudiator.persistance.repositories.ModelService;

public class MonitorInstanceDto extends ModelWithExternalReferenceDto {

    private Long monitor;
    private String apiEndpoint;
    private Long virtualMachine;
    private Long component;
    private Integer port;

    public MonitorInstanceDto() {
        super();
    }

    public MonitorInstanceDto(Long monitor, String apiEndpoint,
                              Long virtualMachine,
                              Long component,
                              Integer port) {
        super();
        this.monitor = monitor;
        this.apiEndpoint = apiEndpoint;
        this.virtualMachine = virtualMachine;
        this.component = component;
        this.port = port;
    }

    @Override public void validation() {
        //validator(Long.class).validate(monitor)
        //        .withValidator(new ModelIdValidator<>(References.monitorService.get()));
    }

    public static class References {
        @Inject public static Provider<ModelService<Monitor>> monitorModelService;
        @Inject public static Provider<ModelService<VirtualMachine>> virtualMachineModelService;
        @Inject public static Provider<ModelService<Component>> componentModelService;
    }

    public Long getMonitor() {
        return monitor;
    }

    public void setMonitor(Long monitor) {
        this.monitor = monitor;
    }

    public Long getVirtualMachine() {
        return virtualMachine;
    }

    public void setVirtualMachine(Long virtualMachine) {
        this.virtualMachine = virtualMachine;
    }

    public Long getComponent() {
        return component;
    }

    public void setComponent(Long component) {
        this.component = component;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public void setApiEndpoint(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
