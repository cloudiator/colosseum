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
import models.Component;
import models.IpAddress;
import models.Monitor;
import models.VirtualMachine;
import models.service.api.generic.ModelService;

import java.util.List;

public class MonitorInstanceDto extends ModelWithExternalReferenceDto {

    private Long monitor;
    private Long ipAddress;
    private Long virtualMachine;
    private Long component;

    public MonitorInstanceDto() {
        super();
    }

    public MonitorInstanceDto(List<String> externalReferences, Long monitor, Long ipAddress,
        Long virtualMachine, Long component) {
        super(externalReferences);
        this.monitor = monitor;
        this.ipAddress = ipAddress;
        this.virtualMachine = virtualMachine;
        this.component = component;
    }

    @Override public void validation() {
        //validator(Long.class).validate(monitor)
        //        .withValidator(new ModelIdValidator<>(References.monitorService.get()));
    }

    public static class References extends ModelWithExternalReferenceDto.References {
        @Inject public static Provider<ModelService<Monitor>> monitorModelService;
        @Inject public static Provider<ModelService<IpAddress>> ipAddressModelService;
        @Inject public static Provider<ModelService<VirtualMachine>> virtualMachineModelService;
        @Inject public static Provider<ModelService<Component>> componentModelService;
    }

    public Long getMonitor() {
        return monitor;
    }

    public void setMonitor(Long monitor) {
        this.monitor = monitor;
    }

    public Long getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(Long ipAddress) {
        this.ipAddress = ipAddress;
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
}
