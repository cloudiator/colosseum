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
import api.dto.ComposedMonitorDto;
import api.binding.AbstractModelToDtoBinding;
import api.binding.transformers.IdToModelTransformer;
import api.binding.transformers.ModelToListIdTransformer;
import api.binding.transformers.StringToExternalReferenceTransformer;
import models.*;
import models.generic.ExternalReference;
import models.service.ModelService;

import java.util.List;


@Singleton public class ComposedMonitorModelToDtoBinding
    extends AbstractModelToDtoBinding<ComposedMonitor, ComposedMonitorDto> {

    private final ModelService<Schedule> scheduleModelService;
    private final ModelService<Window> windowModelService;
    private final ModelService<ScalingAction> scalingActionModelService;
    private final ModelService<Monitor> monitorModelService;
    private final ModelService<FormulaQuantifier> formulaQuantifierModelService;
    private final ModelService<ExternalReference> externalReferenceModelService;
    private final ModelService<MonitorInstance> monitorInstanceModelService;

    @Inject protected ComposedMonitorModelToDtoBinding(
        ModelService<MonitorInstance> monitorInstanceModelService,
        ModelService<Schedule> scheduleModelService,
        ModelService<Window> windowModelService,
        ModelService<ScalingAction> scalingActionModelService,
        ModelService<Monitor> monitorModelService,
        ModelService<FormulaQuantifier> formulaQuantifierModelService,
        ModelService<ExternalReference> externalReferenceModelService) {
        super(ComposedMonitor.class, ComposedMonitorDto.class);
        this.monitorInstanceModelService = monitorInstanceModelService;
        this.scheduleModelService = scheduleModelService;
        this.windowModelService = windowModelService;
        this.scalingActionModelService = scalingActionModelService;
        this.monitorModelService = monitorModelService;
        this.formulaQuantifierModelService = formulaQuantifierModelService;
        this.externalReferenceModelService = externalReferenceModelService;
    }

    @Override public void configure() {
        binding().fromField("flowOperator").toField("flowOperator");
        binding().fromField("function").toField("function");
        binding(Long.class, FormulaQuantifier.class).fromField("quantifier").toField("quantifier")
            .withTransformation(new IdToModelTransformer<>(formulaQuantifierModelService));
        binding(Long.class, Schedule.class).fromField("schedule").toField("schedule")
                .withTransformation(new IdToModelTransformer<>(scheduleModelService));
        binding(Long.class,Window.class ).fromField("window").toField("window")
            .withTransformation(new IdToModelTransformer<>(windowModelService));

        binding(new TypeLiteral<List<Long>>() {
        }, new TypeLiteral<List<Monitor>>() {
        }).fromField("monitors").toField("monitors")
            .withTransformation(
                    new ModelToListIdTransformer<>(new IdToModelTransformer<>(monitorModelService)));

        binding(new TypeLiteral<List<Long>>() {
        }, new TypeLiteral<List<ScalingAction>>() {
        }).fromField("scalingActions").toField("scalingActions")
            .withTransformation(new ModelToListIdTransformer<>(
                    new IdToModelTransformer<>(scalingActionModelService)));

        binding(new TypeLiteral<List<String>>() {
        }, new TypeLiteral<List<ExternalReference>>() {
        }).fromField("externalReferences").toField("externalReferences")
            .withTransformation(new StringToExternalReferenceTransformer());

//TODO only one-way conversion
        binding(new TypeLiteral<List<Long>>() {
        }, new TypeLiteral<List<MonitorInstance>>() {
        }).fromField( "monitorInstances").toField("monitorInstances")
            .withTransformation(
                new ModelToListIdTransformer<>(new IdToModelTransformer<>(monitorInstanceModelService)));
    }
}
