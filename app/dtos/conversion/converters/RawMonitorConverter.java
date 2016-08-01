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

import dtos.RawMonitorDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import dtos.conversion.transformers.ModelToListIdTransformer;
import dtos.conversion.transformers.Transformer;
import dtos.generic.KeyValue;
import dtos.generic.KeyValues;
import models.Application;
import models.Cloud;
import models.Component;
import models.Instance;
import models.MonitorInstance;
import models.RawMonitor;
import models.Schedule;
import models.SensorConfigurations;
import models.SensorDescription;
import models.service.ModelService;


@Singleton public class RawMonitorConverter extends AbstractConverter<RawMonitor, RawMonitorDto> {

    private final ModelService<Application> applicationModelService;
    private final ModelService<Component> componentModelService;
    private final ModelService<Instance> instanceModelService;
    private final ModelService<Cloud> cloudModelService;
    private final ModelService<SensorDescription> sensorDescriptionModelService;
    private final ModelService<Schedule> scheduleModelService;
    private final ModelService<MonitorInstance> monitorInstanceModelService;
    private final ModelService<SensorConfigurations> sensorConfigurationsModelService;

    @Inject protected RawMonitorConverter(ModelService<Application> applicationModelService,
        ModelService<Component> componentModelService, ModelService<Instance> instanceModelService,
        ModelService<Cloud> cloudModelService,
        ModelService<SensorDescription> sensorDescriptionModelService,
        ModelService<Schedule> scheduleModelService,
        ModelService<MonitorInstance> monitorInstanceModelService,
        ModelService<SensorConfigurations> sensorConfigurationsModelService) {
        super(RawMonitor.class, RawMonitorDto.class);
        this.applicationModelService = applicationModelService;
        this.componentModelService = componentModelService;
        this.instanceModelService = instanceModelService;
        this.cloudModelService = cloudModelService;
        this.sensorDescriptionModelService = sensorDescriptionModelService;
        this.scheduleModelService = scheduleModelService;
        this.monitorInstanceModelService = monitorInstanceModelService;
        this.sensorConfigurationsModelService = sensorConfigurationsModelService;
    }

    @Override public void configure() {
        binding(Long.class, Application.class).fromField("application").toField("application")
            .withTransformation(new IdToModelTransformer<>(applicationModelService));
        binding(Long.class, Component.class).fromField("component").toField("component")
            .withTransformation(new IdToModelTransformer<>(componentModelService));
        binding(Long.class, Instance.class).fromField("componentInstance").toField("componentInstance")
            .withTransformation(new IdToModelTransformer<>(instanceModelService));
        binding(Long.class, Cloud.class).fromField("cloud").toField("cloud")
            .withTransformation(new IdToModelTransformer<>(cloudModelService));
        binding(Long.class, SensorDescription.class).fromField("sensorDescription")
            .toField("sensorDescription")
            .withTransformation(new IdToModelTransformer<>(sensorDescriptionModelService));
        binding(Long.class, Schedule.class).fromField("schedule").toField("schedule")
            .withTransformation(new IdToModelTransformer<>(scheduleModelService));
        binding(Long.class, SensorConfigurations.class).fromField("sensorConfigurations")
                .toField("sensorConfigurations")
                .withTransformation(new IdToModelTransformer<>(sensorConfigurationsModelService));

// TODO: a one-way convertion is needed here:
        binding(new TypeLiteral<List<Long>>() {
        }, new TypeLiteral<List<MonitorInstance>>() {
        }).fromField("monitorInstances").toField("monitorInstances")
                .withTransformation(
                    new ModelToListIdTransformer<>(new IdToModelTransformer<>(monitorInstanceModelService)));

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
    }
}
