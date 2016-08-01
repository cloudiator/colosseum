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
import com.google.inject.TypeLiteral;

import java.util.List;
import java.util.Map;

import dtos.MonitorInstanceDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import dtos.conversion.transformers.Transformer;
import dtos.generic.KeyValue;
import dtos.generic.KeyValues;
import models.Component;
import models.IpAddress;
import models.Monitor;
import models.MonitorInstance;
import models.VirtualMachine;
import models.service.ModelService;


@Singleton public class MonitorInstanceConverter
    extends AbstractConverter<MonitorInstance, MonitorInstanceDto> {

    private final ModelService<Monitor> monitorModelService;
    private final ModelService<IpAddress> ipAddressModelService;
    private final ModelService<VirtualMachine> virtualMachineModelService;
    private final ModelService<Component> componentModelService;

    @Inject protected MonitorInstanceConverter(ModelService<Monitor> monitorModelService,
        ModelService<IpAddress> ipAddressModelService,
        ModelService<VirtualMachine> virtualMachineModelService,
        ModelService<Component> componentModelService) {
        super(MonitorInstance.class, MonitorInstanceDto.class);
        this.monitorModelService = monitorModelService;
        this.ipAddressModelService = ipAddressModelService;
        this.virtualMachineModelService = virtualMachineModelService;
        this.componentModelService = componentModelService;
    }

    @Override public void configure() {
        binding(Long.class, Component.class).fromField("component").toField("component")
            .withTransformation(new IdToModelTransformer<>(componentModelService));
        binding(Long.class, Monitor.class).fromField("monitor").toField("monitor")
            .withTransformation(new IdToModelTransformer<>(monitorModelService));
        binding().fromField("apiEndpoint").toField("apiEndpoint");
        binding(Long.class, IpAddress.class).fromField("ipAddress").toField("ipAddress")
            .withTransformation(new IdToModelTransformer<>(ipAddressModelService));
        binding(Long.class, VirtualMachine.class).fromField("virtualMachine").toField("virtualMachine")
            .withTransformation(new IdToModelTransformer<>(virtualMachineModelService));
        binding(new TypeLiteral<List<KeyValue>>() {
        }, new TypeLiteral<Map<String, String>>() {
        }).fromField("externalReferences").toField("externalReferences").withTransformation(
                new Transformer<List<KeyValue>, Map<String, String>>() {
                    @Override public Map<String, String> transform(List<KeyValue> tags) {
                        return KeyValues.to(tags);
                    }

                    @Override public List<KeyValue> transformReverse(
                            Map<String, String> stringStringMap) {
                        return KeyValues.of(stringStringMap);
                    }
                });
        binding().fromField("port").toField("port");
    }
}
