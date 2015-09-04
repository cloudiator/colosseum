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

package dtos.conversion.converters;

import com.google.inject.Inject;
import dtos.IpAddressDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import models.IpAddress;
import models.VirtualMachine;
import models.service.ModelService;

/**
 * Created by daniel on 14.04.15.
 */
public class IpAddressConverter extends AbstractConverter<IpAddress, IpAddressDto> {

    private final ModelService<VirtualMachine> virtualMachineModelService;

    @Inject protected IpAddressConverter(ModelService<VirtualMachine> virtualMachineModelService) {
        super(IpAddress.class, IpAddressDto.class);
        this.virtualMachineModelService = virtualMachineModelService;
    }

    @Override public void configure() {
        binding().fromField("ip").toField("ip");
        binding().fromField("ipType").toField("ipType");
        binding(Long.class, VirtualMachine.class).fromField("virtualMachine")
            .toField("virtualMachine")
            .withTransformation(new IdToModelTransformer<>(virtualMachineModelService));
    }
}
