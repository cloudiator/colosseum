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
import dtos.generic.MonitorDto;
import dtos.validation.validators.ModelIdValidator;
import models.*;
import models.scalability.FlowOperator;
import models.scalability.FormulaOperator;
import models.service.ModelService;

import java.util.List;

public class ComposedMonitorDto extends MonitorDto {

    private FlowOperator flowOperator;
    private FormulaOperator function;
    private Long quantifier;
    private Long schedule;
    private Long window;
    private List<Long> monitors;
    private List<Long> scalingActions;


    public ComposedMonitorDto() {
        super();
    }

    public ComposedMonitorDto(List<String> externalReferences, List<Long> monitorInstances,
        FlowOperator flowOperator, FormulaOperator function, Long quantifier, Long schedule,
        Long window, List<Long> monitors, List<Long> scalingActions) {
        super(externalReferences, monitorInstances);
        this.flowOperator = flowOperator;
        this.function = function;
        this.quantifier = quantifier;
        this.schedule = schedule;
        this.window = window;
        this.monitors = monitors;
        this.scalingActions = scalingActions;
    }

    @Override public void validation() {
        super.validation();

        // TODO List?
        //validator(Long.class).validate(monitors)
        //        .withValidator(new ModelIdValidator<>(References.monitorAddressService.get()));
        //validator(Long.class).validate(scalingActions)
        //        .withValidator(new ModelIdValidator<>(References.scalingActionService.get()));
        validator(Long.class).validate(quantifier)
            .withValidator(new ModelIdValidator<>(References.formulaQuantifierService.get()));
        validator(Long.class).validate(window)
            .withValidator(new ModelIdValidator<>(References.windowService.get()));
        validator(Long.class).validate(schedule)
            .withValidator(new ModelIdValidator<>(References.scheduleService.get()));
    }

    public static class References extends MonitorDto.References {
        @Inject public static Provider<ModelService<Schedule>> scheduleService;
        @Inject public static Provider<ModelService<Window>> windowService;
        @Inject public static Provider<ModelService<Monitor>> monitorService;
        @Inject public static Provider<ModelService<ScalingAction>> scalingActionService;
        @Inject public static Provider<ModelService<FormulaQuantifier>> formulaQuantifierService;
    }

    public FlowOperator getFlowOperator() {
        return flowOperator;
    }

    public void setFlowOperator(FlowOperator flowOperator) {
        this.flowOperator = flowOperator;
    }

    public FormulaOperator getFunction() {
        return function;
    }

    public void setFunction(FormulaOperator function) {
        this.function = function;
    }

    public Long getQuantifier() {
        return quantifier;
    }

    public void setQuantifier(Long quantifier) {
        this.quantifier = quantifier;
    }

    public Long getSchedule() {
        return schedule;
    }

    public void setSchedule(Long schedule) {
        this.schedule = schedule;
    }

    public Long getWindow() {
        return window;
    }

    public void setWindow(Long window) {
        this.window = window;
    }

    public List<Long> getMonitors() {
        return monitors;
    }

    public void setMonitors(List<Long> monitors) {
        this.monitors = monitors;
    }

    public List<Long> getScalingActions() {
        return scalingActions;
    }

    public void setScalingActions(List<Long> scalingActions) {
        this.scalingActions = scalingActions;
    }
}
