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
import com.google.inject.Singleton;
import dtos.MonitorInstanceDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import dtos.conversion.transformers.StringToExternalReferenceTransformer;
import models.*;
import models.generic.ExternalReference;
import models.service.ModelService;

import java.util.List;


@Singleton public class MonitorInstanceConverter
    extends AbstractConverter<MonitorInstance, MonitorInstanceDto> {

    private final ModelService<Monitor> monitorModelService;
    private final ModelService<IpAddress> ipAddressModelService;
    private final ModelService<VirtualMachine> virtualMachineModelService;
    private final ModelService<Component> componentModelService;
    private final ModelService<ExternalReference> externalReferenceModelService;

    @Inject protected MonitorInstanceConverter(ModelService<Monitor> monitorModelService,
        ModelService<IpAddress> ipAddressModelService,
        ModelService<VirtualMachine> virtualMachineModelService,
        ModelService<Component> componentModelService,
        ModelService<ExternalReference> externalReferenceModelService) {
        super(MonitorInstance.class, MonitorInstanceDto.class);
        this.monitorModelService = monitorModelService;
        this.ipAddressModelService = ipAddressModelService;
        this.virtualMachineModelService = virtualMachineModelService;
        this.componentModelService = componentModelService;
        this.externalReferenceModelService = externalReferenceModelService;
    }

    @Override public void configure() {
        builder().from(Long.class, "component").to(Component.class, "component")
            .withTransformation(new IdToModelTransformer<>(componentModelService));
        builder().from(Long.class, "monitor").to(Monitor.class, "monitor")
            .withTransformation(new IdToModelTransformer<>(monitorModelService));
        builder().from("apiEndpoint").to("apiEndpoint");
        builder().from(Long.class, "ipAddress").to(IpAddress.class, "ipAddress")
            .withTransformation(new IdToModelTransformer<>(ipAddressModelService));
        builder().from(Long.class, "virtualMachine").to(VirtualMachine.class, "virtualMachine")
            .withTransformation(new IdToModelTransformer<>(virtualMachineModelService));
        builder().from(List.class, "externalReferences").to(List.class, "externalReferences")
            .withUnsafeTransformation(new StringToExternalReferenceTransformer());
    }
}
