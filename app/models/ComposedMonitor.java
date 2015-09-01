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

package models;

import components.scalability.internal.ComposedMonitorTsdbLocator;
import components.scalability.internal.TsdbLocator;
import models.scalability.FlowOperator;
import models.scalability.FormulaOperator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 20.05.2015.
 */
@Entity public class ComposedMonitor extends MetricMonitor {

    @Enumerated(EnumType.STRING) private FlowOperator flowOperator;
    @Enumerated(EnumType.STRING) private FormulaOperator function;

    /* Actually is not optional but due to a inheritance bug this is now optional */
    @ManyToOne(optional = true) private FormulaQuantifier quantifier;
    @ManyToOne(optional = true) private Window window;
    @ManyToMany private List<Monitor> monitors = new ArrayList<>();
    @ManyToMany private List<ScalingAction> scalingActions = new ArrayList<>();

    /**
     * Empty constructor for hibernate.
     */
    protected ComposedMonitor() {
    }

    public ComposedMonitor(Schedule schedule, FlowOperator flowOperator, FormulaOperator function,
        FormulaQuantifier quantifier, Window window, List<Monitor> monitors) {
        super(schedule);
        this.flowOperator = flowOperator;
        this.function = function;
        this.quantifier = quantifier;
        this.window = window;
        this.monitors = monitors;
    }

    public FlowOperator getFlowOperator() {
        return flowOperator;
    }

    public FormulaOperator getFunction() {
        return function;
    }

    public FormulaQuantifier getQuantifier() {
        return quantifier;
    }

    public Window getWindow() {
        return window;
    }

    public List<Monitor> getMonitors() {
        return monitors;
    }

    public List<ScalingAction> getScalingActions() {
        return scalingActions;
    }

    public void setScalingActions(List<ScalingAction> scalingActions) {
        this.scalingActions = scalingActions;
    }

    public void addScalingActions(ScalingAction scalingAction) {
        this.scalingActions.add(scalingAction);
    }

    @Override protected TsdbLocator getTsdbLocator() {
        return new ComposedMonitorTsdbLocator(this);
    }
}
