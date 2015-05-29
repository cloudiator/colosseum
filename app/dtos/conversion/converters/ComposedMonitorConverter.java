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
import dtos.CloudDto;
import dtos.ComposedMonitorDto;
import dtos.ScheduleDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.generic.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import models.*;
import models.service.api.generic.ModelService;


@Singleton public class ComposedMonitorConverter extends
    AbstractConverter<ComposedMonitor, ComposedMonitorDto> {

    private final ModelService<Schedule> scheduleModelService;
    private final ModelService<Window> windowModelService;
    private final ModelService<ScalingAction> scalingActionModelService;
    private final ModelService<Monitor> monitorModelService;
    private final ModelService<FormulaQuantifier> formulaQuantifierModelService;

    @Inject protected ComposedMonitorConverter(ModelService<Schedule> scheduleModelService, ModelService<Window> windowModelService, ModelService<ScalingAction> scalingActionModelService, ModelService<Monitor> monitorModelService, ModelService<FormulaQuantifier> formulaQuantifierModelService) {
        super(ComposedMonitor.class, ComposedMonitorDto.class);
        this.scheduleModelService = scheduleModelService;
        this.windowModelService = windowModelService;
        this.scalingActionModelService = scalingActionModelService;
        this.monitorModelService = monitorModelService;
        this.formulaQuantifierModelService = formulaQuantifierModelService;
    }

    @Override public void configure() {
        builder().from("flowOperator").to("flowOperator");
        builder().from("function").to("function");
        builder().from("quantifier").to("quantifier");
        builder().from(Long.class, "schedule").to(Schedule.class, "schedule")
                .withTransformation(new IdToModelTransformer<>(scheduleModelService));
        builder().from(Long.class, "window").to(Window.class, "window")
                .withTransformation(new IdToModelTransformer<>(windowModelService));
        builder().from(Long.class, "monitors").to(Monitor.class, "monitors")
                .withTransformation(new IdToModelTransformer<>(monitorModelService));
        builder().from(Long.class, "scalingActions").to(ScalingAction.class, "scalingActions")
                .withTransformation(new IdToModelTransformer<>(scalingActionModelService));
    }
}
