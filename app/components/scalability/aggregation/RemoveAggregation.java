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

import de.uniulm.omi.cloudiator.axe.aggregator.communication.rmi.AggregatorServiceAccess;

import java.rmi.RemoteException;

import models.ComposedMonitor;
import models.Monitor;
import models.service.ModelService;
import play.db.jpa.JPAApi;

/**
 * Created by Frank on 30.07.2015.
 */
public class RemoveAggregation extends MonitorAggregation {
    private final ModelService<ComposedMonitor> composedMonitorModelService;
    private final JPAApi jpaApi;

    public RemoveAggregation(Monitor monitor, ModelService<ComposedMonitor> composedMonitorModelService, JPAApi jpaApi) {
        super(monitor);
        this.composedMonitorModelService = composedMonitorModelService;
        this.jpaApi = jpaApi;
    }

    @Override
    public void execute(AggregatorServiceAccess service) {
        try {
            service.stopAggregation(getObject().getId());

            try {
                jpaApi.withTransaction(() -> {
                    ComposedMonitor entity = composedMonitorModelService.getById(getObject().getId());

                    if (entity == null) {
                        LOGGER.info("Could not remove stopped ComposedMonitor from DB, since it was not available.");
                        return;
                    }

                    composedMonitorModelService.delete(entity);
                });
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }

        } catch (RemoteException e) {
            LOGGER.error("Error when calling remote object to stop aggregation.");
        }
    }
}
