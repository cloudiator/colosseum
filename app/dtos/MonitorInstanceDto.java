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
import dtos.generic.ValidatableDto;
import dtos.validation.ExpressionValidator;
import dtos.validation.ModelIdValidator;
import dtos.validation.NotNullOrEmptyValidator;
import models.*;
import models.service.api.generic.ModelService;

public class MonitorInstanceDto extends ValidatableDto {

    private Long monitor;
    private Long ipAddress;
    private Long virtualMachine;
    private Long component;

    public MonitorInstanceDto() {
        super();
    }

    public MonitorInstanceDto(Long monitor, Long ipAddress, Long virtualMachine, Long component) {
        this.monitor = monitor;
        this.ipAddress = ipAddress;
        this.virtualMachine = virtualMachine;
        this.component = component;
    }

    @Override public void validation() {
        validator(Long.class).validate(monitor)
                .withValidator(new ModelIdValidator<>(References.monitorAddressService.get()));
    }

    public static class References {
        @Inject public static Provider<ModelService<Monitor>> monitorAddressService;
        @Inject public static Provider<ModelService<IpAddress>> ipAddressService;
        @Inject public static Provider<ModelService<VirtualMachine>> virtualMachineService;
        @Inject public static Provider<ModelService<Component>> componentService;
    }
}
