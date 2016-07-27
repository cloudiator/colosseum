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

package components.scalability;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import models.ComponentHorizontalInScalingAction;
import models.ComponentHorizontalOutScalingAction;
import models.ComposedMonitor;
import models.ConstantMonitor;
import models.FormulaQuantifier;
import models.MeasurementWindow;
import models.Monitor;
import models.RawMonitor;
import models.ScalingAction;
import models.Schedule;
import models.SensorDescription;
import models.TimeWindow;
import models.Window;
import models.scalability.FilterType;
import models.scalability.FlowOperator;
import models.scalability.FormulaOperator;

/**
 * Created by Frank on 28.07.2015.
 */
public class AggregatorEntitiesConverter {
    public static de.uniulm.omi.cloudiator.axe.aggregator.entities.ComposedMonitor convert(
        ComposedMonitor cm) {
        return new de.uniulm.omi.cloudiator.axe.aggregator.entities.ComposedMonitor(cm.getId(),
            convert(cm.getFlowOperator()), convert(cm.getSchedule()), convert(cm.getFunction()),
            convert(cm.getQuantifier()), convert(cm.getWindow()), convert(cm.getMonitors()),
            convertScalingActions(cm.getScalingActions()));
    }

    public static de.uniulm.omi.cloudiator.axe.aggregator.entities.RawMonitor convert(
        RawMonitor mon) {
        return new de.uniulm.omi.cloudiator.axe.aggregator.entities.RawMonitor(mon.getId(),
            convert(mon.getSchedule()),
            (mon.getApplication() == null ? -1 : mon.getApplication().getId()),
            (mon.getComponent() == null ? -1 : mon.getComponent().getId()),
            (mon.getComponentInstance() == null ? -1 : mon.getComponentInstance().getId()),
            (mon.getCloud() == null ? -1 : mon.getCloud().getId()),
            convert(mon.getSensorDescription()));
    }

    public static List<de.uniulm.omi.cloudiator.axe.aggregator.entities.Monitor> convert(
        List<Monitor> obj) {
        List<de.uniulm.omi.cloudiator.axe.aggregator.entities.Monitor> result = new ArrayList<>();

        for (Monitor mon : obj) {
            result.add(convert(mon));
        }

        return result;
    }

    public static de.uniulm.omi.cloudiator.axe.aggregator.entities.Monitor convert(Monitor mon) {
        if (mon instanceof RawMonitor) {
            return (convert((RawMonitor) mon));
        } else if (mon instanceof ComposedMonitor) {
            return (convert((ComposedMonitor) mon));
        } else { //ConstantMonitor TODO fix this
            return (new de.uniulm.omi.cloudiator.axe.aggregator.entities.ConstantMonitor(
                mon.getId(), ((ConstantMonitor) mon).getValue()));
        }
    }

    public static de.uniulm.omi.cloudiator.axe.aggregator.entities.SensorDescription convert(
        SensorDescription obj) {
        return new de.uniulm.omi.cloudiator.axe.aggregator.entities.SensorDescription(obj.getId(),
            obj.getClassName(), obj.getMetricName(), obj.isVmSensor(), obj.isPush());
    }

    public static de.uniulm.omi.cloudiator.axe.aggregator.entities.FlowOperator convert(
        FlowOperator fo) {
        switch (fo) {
            case MAP:
                return de.uniulm.omi.cloudiator.axe.aggregator.entities.FlowOperator.MAP;
            case REDUCE:
                return de.uniulm.omi.cloudiator.axe.aggregator.entities.FlowOperator.REDUCE;
            default:
                throw new InvalidParameterException("Parameter is not a valid FlowOperator class: " + fo);
        }
    }

    public static de.uniulm.omi.cloudiator.axe.aggregator.entities.FormulaOperator convert(
        FormulaOperator fo) {
        return de.uniulm.omi.cloudiator.axe.aggregator.entities.FormulaOperator
            .valueOf(fo.toString());
    }

    public static de.uniulm.omi.cloudiator.axe.aggregator.entities.Schedule convert(Schedule obj) {
        return new de.uniulm.omi.cloudiator.axe.aggregator.entities.Schedule(obj.getId(),
            obj.getInterval().intValue(), obj.getTimeUnit());
    }

    public static de.uniulm.omi.cloudiator.axe.aggregator.entities.FormulaQuantifier convert(
        FormulaQuantifier obj) {
        return new de.uniulm.omi.cloudiator.axe.aggregator.entities.FormulaQuantifier(
            obj.getRelative(), obj.getValue());
    }

    public static de.uniulm.omi.cloudiator.axe.aggregator.entities.Window convert(Window obj) {
        if (obj instanceof TimeWindow) {
            return new de.uniulm.omi.cloudiator.axe.aggregator.entities.TimeWindow(obj.getId(),
                ((TimeWindow) obj).getInterval().intValue(), //TODO: use int or long in both
                ((TimeWindow) obj).getTimeUnit());
        } else if (obj instanceof MeasurementWindow) {
            return new de.uniulm.omi.cloudiator.axe.aggregator.entities.MeasurementWindow(
                obj.getId(), ((MeasurementWindow) obj).getMeasurements().intValue() //TODO: use int or long in both
            );
        } else {
            throw new InvalidParameterException("Parameter is not of valid Window class: " + obj);
        }
    }

    public static de.uniulm.omi.cloudiator.axe.aggregator.entities.FormulaOperator convert(
        FilterType obj) {
        switch (obj) {
            case LTE:
                return de.uniulm.omi.cloudiator.axe.aggregator.entities.FormulaOperator.LTE;
            case LT:
                return de.uniulm.omi.cloudiator.axe.aggregator.entities.FormulaOperator.LT;
            case E:
                return de.uniulm.omi.cloudiator.axe.aggregator.entities.FormulaOperator.EQ;
            case GT:
                return de.uniulm.omi.cloudiator.axe.aggregator.entities.FormulaOperator.GT;
            case GTE:
                return de.uniulm.omi.cloudiator.axe.aggregator.entities.FormulaOperator.GTE;
        }

        return de.uniulm.omi.cloudiator.axe.aggregator.entities.FormulaOperator.SUM; /* TODO add 'ANY' to formula operator enums */
    }

    public static List<de.uniulm.omi.cloudiator.axe.aggregator.entities.ScalingAction> convertScalingActions(
        List<ScalingAction> obj) {
        List<de.uniulm.omi.cloudiator.axe.aggregator.entities.ScalingAction> result =
            new ArrayList();

        for (ScalingAction sa : obj) {
            de.uniulm.omi.cloudiator.axe.aggregator.entities.ScalingAction _sa = convert(sa);
            if (_sa != null) {
                result.add(_sa);
            }
        }

        return result;
    }


    public static de.uniulm.omi.cloudiator.axe.aggregator.entities.ScalingAction convert(
        ScalingAction sa) {
        if (sa instanceof ComponentHorizontalInScalingAction) {
            return (convert((ComponentHorizontalInScalingAction) sa));
        } else if (sa instanceof ComponentHorizontalOutScalingAction) {
            return (convert((ComponentHorizontalOutScalingAction) sa));
        } else {
            throw new InvalidParameterException("ScalingAction is currently not supported: " + sa);
        }
    }

    public static de.uniulm.omi.cloudiator.axe.aggregator.entities.ComponentHorizontalInScalingAction convert(
        ComponentHorizontalInScalingAction sa) {
        de.uniulm.omi.cloudiator.axe.aggregator.entities.ComponentHorizontalInScalingAction result;

        result =
            new de.uniulm.omi.cloudiator.axe.aggregator.entities.ComponentHorizontalInScalingAction(
                sa.getId(), sa.getAmount(), sa.getMin(), sa.getMax(), sa.getCount(),
                sa.getApplicationComponent().getId());

        return result;
    }

    public static de.uniulm.omi.cloudiator.axe.aggregator.entities.ComponentHorizontalOutScalingAction convert(
        ComponentHorizontalOutScalingAction sa) {
        de.uniulm.omi.cloudiator.axe.aggregator.entities.ComponentHorizontalOutScalingAction result;

        result =
            new de.uniulm.omi.cloudiator.axe.aggregator.entities.ComponentHorizontalOutScalingAction(
                sa.getId(), sa.getAmount(), sa.getMin(), sa.getMax(), sa.getCount(),
                sa.getApplicationComponent().getId());

        return result;
    }
}
