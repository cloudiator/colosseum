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

package dtos.convert.converters.impl;

import com.google.inject.Inject;
import dtos.InstanceDto;
import dtos.convert.impl.BaseConverter;
import models.ApplicationComponent;
import models.Instance;
import models.VirtualMachine;
import models.service.impl.ApplicationComponentServiceImpl;
import models.service.impl.VirtualMachineServiceImpl;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel seybold on 17.12.2014.
 */
public class InstanceConverter extends BaseConverter<Instance, InstanceDto> {

    private final ApplicationComponentServiceImpl applicationComponentServiceImpl;
    private final VirtualMachineServiceImpl virtualMachineServiceImpl;

    @Inject
    InstanceConverter(ApplicationComponentServiceImpl applicationComponentServiceImpl, VirtualMachineServiceImpl virtualMachineServiceImpl) {
        checkNotNull(applicationComponentServiceImpl);
        checkNotNull(virtualMachineServiceImpl);

        this.applicationComponentServiceImpl = applicationComponentServiceImpl;
        this.virtualMachineServiceImpl = virtualMachineServiceImpl;
    }

    @Override
    public Instance toModel(InstanceDto dto, Instance model) {
        checkNotNull(dto);
        checkNotNull(model);
        ApplicationComponent applicationComponent = applicationComponentServiceImpl.getById(dto.getApplicationComponent());
        checkState(applicationComponent != null, "Could not retrieve applicationComponent for id: " + dto.getApplicationComponent());
        model.setApplicationComponent(applicationComponent);

        VirtualMachine virtualMachine = virtualMachineServiceImpl.getById(dto.getVirtualMachine());
        checkState(virtualMachine != null, "Could not retrieve virtualMachine for id: " + dto.getVirtualMachine());
        model.setVirtualMachine(virtualMachine);

        return model;
    }

    @Override
    public InstanceDto toDto(Instance model) {
        checkNotNull(model);
        return new InstanceDto(model.getApplicationComponent().getId(), model.getVirtualMachine().getId());
    }
}
