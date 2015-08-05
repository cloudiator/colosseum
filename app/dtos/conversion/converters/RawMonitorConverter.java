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
import dtos.RawMonitorDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import dtos.conversion.transformers.StringToExternalReferenceTransformer;
import models.*;
import models.generic.ExternalReference;
import models.service.api.generic.ModelService;

import java.util.List;


@Singleton public class RawMonitorConverter extends AbstractConverter<RawMonitor, RawMonitorDto> {

    private final ModelService<Application> applicationModelService;
    private final ModelService<Component> componentModelService;
    private final ModelService<Instance> instanceModelService;
    private final ModelService<Cloud> cloudModelService;
    private final ModelService<SensorDescription> sensorDescriptionModelService;
    private final ModelService<Schedule> scheduleModelService;
    private final ModelService<ExternalReference> externalReferenceModelService;

    @Inject protected RawMonitorConverter(ModelService<Application> applicationModelService,
        ModelService<Component> componentModelService, ModelService<Instance> instanceModelService,
        ModelService<Cloud> cloudModelService,
        ModelService<SensorDescription> sensorDescriptionModelService,
        ModelService<Schedule> scheduleModelService,
        ModelService<ExternalReference> externalReferenceModelService) {
        super(RawMonitor.class, RawMonitorDto.class);
        this.applicationModelService = applicationModelService;
        this.componentModelService = componentModelService;
        this.instanceModelService = instanceModelService;
        this.cloudModelService = cloudModelService;
        this.sensorDescriptionModelService = sensorDescriptionModelService;
        this.scheduleModelService = scheduleModelService;
        this.externalReferenceModelService = externalReferenceModelService;
    }

    @Override public void configure() {
        builder().from(Long.class, "application").to(Application.class, "application")
            .withTransformation(new IdToModelTransformer<>(applicationModelService));
        builder().from(Long.class, "component").to(Component.class, "component")
            .withTransformation(new IdToModelTransformer<>(componentModelService));
        builder().from(Long.class, "componentInstance").to(Instance.class, "componentInstance")
            .withTransformation(new IdToModelTransformer<>(instanceModelService));
        builder().from(Long.class, "cloud").to(Cloud.class, "cloud")
            .withTransformation(new IdToModelTransformer<>(cloudModelService));
        builder().from(Long.class, "sensorDescription")
            .to(SensorDescription.class, "sensorDescription")
            .withTransformation(new IdToModelTransformer<>(sensorDescriptionModelService));
        builder().from(Long.class, "schedule").to(Schedule.class, "schedule")
            .withTransformation(new IdToModelTransformer<>(scheduleModelService));
        builder().from(List.class, "externalReferences").to(List.class, "externalReferences")
            .withUnsafeTransformation(new StringToExternalReferenceTransformer());
    }
}
