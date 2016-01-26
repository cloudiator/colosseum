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

package api.binding.binders;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import api.dto.RawMonitorDto;
import api.binding.AbstractModelToDtoBinding;
import api.binding.transformers.IdToModelTransformer;
import api.binding.transformers.ModelToListIdTransformer;
import api.binding.transformers.StringToExternalReferenceTransformer;
import models.*;
import models.generic.ExternalReference;
import models.service.ModelService;

import java.util.List;


@Singleton public class RawMonitorModelToDtoBinding
    extends AbstractModelToDtoBinding<RawMonitor, RawMonitorDto> {

    private final ModelService<Application> applicationModelService;
    private final ModelService<Component> componentModelService;
    private final ModelService<Instance> instanceModelService;
    private final ModelService<Cloud> cloudModelService;
    private final ModelService<SensorDescription> sensorDescriptionModelService;
    private final ModelService<Schedule> scheduleModelService;
    private final ModelService<ExternalReference> externalReferenceModelService;
    private final ModelService<MonitorInstance> monitorInstanceModelService;

    @Inject protected RawMonitorModelToDtoBinding(ModelService<Application> applicationModelService,
        ModelService<Component> componentModelService, ModelService<Instance> instanceModelService,
        ModelService<Cloud> cloudModelService,
        ModelService<SensorDescription> sensorDescriptionModelService,
        ModelService<Schedule> scheduleModelService,
        ModelService<ExternalReference> externalReferenceModelService,
        ModelService<MonitorInstance> monitorInstanceModelService) {
        super(RawMonitor.class, RawMonitorDto.class);
        this.applicationModelService = applicationModelService;
        this.componentModelService = componentModelService;
        this.instanceModelService = instanceModelService;
        this.cloudModelService = cloudModelService;
        this.sensorDescriptionModelService = sensorDescriptionModelService;
        this.scheduleModelService = scheduleModelService;
        this.externalReferenceModelService = externalReferenceModelService;
        this.monitorInstanceModelService = monitorInstanceModelService;
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

// TODO: a one-way convertion is needed here:
        binding(new TypeLiteral<List<Long>>() {
        }, new TypeLiteral<List<MonitorInstance>>() {
        }).fromField("monitorInstances").toField("monitorInstances")
                .withTransformation(
                    new ModelToListIdTransformer<>(new IdToModelTransformer<>(monitorInstanceModelService)));

        binding(new TypeLiteral<List<String>>() {
        }, new TypeLiteral<List<ExternalReference>>() {
        }).
                fromField("externalReferences").
                toField("externalReferences")
                .withTransformation(
                        new StringToExternalReferenceTransformer());
    }
}
