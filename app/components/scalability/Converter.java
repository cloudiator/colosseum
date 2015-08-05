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

import models.*;
import models.scalability.FilterType;
import models.scalability.FlowOperator;
import models.scalability.FormulaOperator;
import models.scalability.SubscriptionType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 28.07.2015.
 */
public class Converter {
    public static de.uniulm.omi.executionware.srl.api.ComposedMonitor convert(ComposedMonitor cm){
        return new de.uniulm.omi.executionware.srl.api.ComposedMonitor(
            cm.getId(),
            convert(cm.getFlowOperator()),
            convert(cm.getSchedule()),
            convert(cm.getFunction()),
            convert(cm.getQuantifier()),
            convert(cm.getWindow()),
            convert(cm.getMonitors())
        );
    }
    public static de.uniulm.omi.executionware.srl.api.RawMonitor convert(RawMonitor mon){
        return
            new de.uniulm.omi.executionware.srl.api.RawMonitor(
                mon.getId(),
                convert(mon.getSchedule()),
                (mon.getApplication() == null ? -1 : mon.getApplication().getId()),
                (mon.getComponent() == null ? -1 : mon.getComponent().getId()),
                (mon.getComponentInstance() == null ? -1 : mon.getComponentInstance().getId()),
                (mon.getCloud() == null ? -1 : mon.getCloud().getId()),
                convert(mon.getSensorDescription())
        );
    }

    public static List<de.uniulm.omi.executionware.srl.api.Monitor> convert(List<Monitor> obj){
        List<de.uniulm.omi.executionware.srl.api.Monitor> result = new ArrayList();

        for(Monitor mon : obj) {
            result.add(convert(mon));
        }

        return result;
    }

    public static de.uniulm.omi.executionware.srl.api.Monitor convert(Monitor mon){
        if (mon instanceof RawMonitor) {
            return (convert((RawMonitor) mon));
        } else if (mon instanceof ComposedMonitor) {
            return (convert((ComposedMonitor)mon));
        } else { //ConstantMonitor TODO fix this
            return (new de.uniulm.omi.executionware.srl.api.ConstantMonitor(
                    mon.getId(),
                    ((ConstantMonitor) mon).getValue()
                )
            );
        }
    }

    public static de.uniulm.omi.executionware.srl.api.SensorDescription convert(SensorDescription obj){
        return new de.uniulm.omi.executionware.srl.api.SensorDescription(
            obj.getId(),
            obj.getClassName(),
            obj.getMetricName(),
            obj.isVmSensor()
        );
    }

    public static de.uniulm.omi.executionware.srl.api.FlowOperator convert(FlowOperator fo){
        switch (fo){
            case MAP: return de.uniulm.omi.executionware.srl.api.FlowOperator.MAP;
            case REDUCE: return de.uniulm.omi.executionware.srl.api.FlowOperator.REDUCE;
            default : return null; //TODO exception
        }
    }

    public static de.uniulm.omi.executionware.srl.api.FormulaOperator convert(FormulaOperator fo){
        return de.uniulm.omi.executionware.srl.api.FormulaOperator.valueOf(fo.toString());
    }

    public static de.uniulm.omi.executionware.srl.api.Schedule convert(Schedule obj){
        return new de.uniulm.omi.executionware.srl.api.Schedule(
            obj.getId(),
            obj.getInterval().intValue(),
            obj.getTimeUnit()
        );
    }

    public static de.uniulm.omi.executionware.srl.api.FormulaQuantifier convert(FormulaQuantifier obj){
        return new de.uniulm.omi.executionware.srl.api.FormulaQuantifier(
            obj.getRelative(),
            obj.getValue()
        );
    }

    public static de.uniulm.omi.executionware.srl.api.Window convert(Window obj){
        if(obj instanceof TimeWindow){
            return new de.uniulm.omi.executionware.srl.api.TimeWindow(
                obj.getId(),
                ((TimeWindow)obj).getInterval().intValue(), //TODO
                ((TimeWindow)obj).getTimeUnit()
            );
        } else { //MeasurmentWindow TODO fix this
            return new de.uniulm.omi.executionware.srl.api.MeasurementWindow(
                obj.getId(),
                ((MeasurementWindow)obj).getMeasurements().intValue() //TODO
            );
        }
    }

    public static de.uniulm.omi.executionware.srl.api.FormulaOperator convert(FilterType obj){
        switch(obj){
            case LTE : return de.uniulm.omi.executionware.srl.api.FormulaOperator.LTE;
            case LT : return de.uniulm.omi.executionware.srl.api.FormulaOperator.LT;
            case E : return de.uniulm.omi.executionware.srl.api.FormulaOperator.EQ;
            case GT : return de.uniulm.omi.executionware.srl.api.FormulaOperator.GT;
            case GTE : return de.uniulm.omi.executionware.srl.api.FormulaOperator.GTE;
        }

        return de.uniulm.omi.executionware.srl.api.FormulaOperator.SUM; /* TODO add any to formula operator enums */
    }
}
