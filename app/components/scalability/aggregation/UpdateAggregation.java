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

package components.scalability.aggregation;

import de.uniulm.omi.executionware.srl.aggregator.AggregatorService;
import de.uniulm.omi.executionware.srl.aggregator.communication.rmi.AggregatorServiceAccess;
import models.ComposedMonitor;

/**
 * Created by Frank on 19.08.2015.
 */
public class UpdateAggregation extends MonitorAggregation {

    public UpdateAggregation(ComposedMonitor composedMonitor) {
        super(composedMonitor);
    }

    @Override public void execute(AggregatorServiceAccess service) {
        try {
            service.stopAggregation(this.getObject().getId());
            service.doAggregation(this.getObject().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
