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

package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import components.scalability.AgentCommunicator;
import components.scalability.FrontendCommunicator;
import components.scalability.ScalingEngine;
import controllers.generic.GenericApiController;
import de.uniulm.omi.cloudiator.visor.client.entities.Monitor;
import dtos.RawMonitorDto;
import dtos.conversion.ModelDtoConversionService;
import models.MonitorInstance;
import models.RawMonitor;
import models.service.api.generic.ModelService;
import play.db.jpa.Transactional;
import play.mvc.Result;

import java.util.concurrent.TimeUnit;

/**
 * Implementation of the GenericApiController for the RawMonitor model class.
 *
 * @author Daniel Baur
 */
public class RawMonitorController
    extends GenericApiController<RawMonitor, RawMonitorDto, RawMonitorDto, RawMonitorDto> {
    ScalingEngine se;
    ModelService<MonitorInstance> monitorInstanceModelService;

    /**
     * Constructs a GenericApiController.
     *
     * @param modelService      the model service for retrieving the models.
     * @param typeLiteral       a type literal for the model type
     * @param conversionService the conversion service for converting models and dtos.
     * @throws NullPointerException if any of the above parameters is null.
     */
    @Inject public RawMonitorController(ModelService<RawMonitor> modelService,
        TypeLiteral<RawMonitor> typeLiteral, ModelDtoConversionService conversionService,
        ScalingEngine se, ModelService<MonitorInstance> monitorInstanceModelService) {
        super(modelService, typeLiteral, conversionService);

        this.monitorInstanceModelService = monitorInstanceModelService;
        this.se = se;
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.RawMonitorController.get(id).absoluteURL(request());
    }

    @Override @Transactional protected void postPost(RawMonitor entity) {
        super.postPost(entity);

        se._doMonitorVms(
            (entity.getApplication() == null ? -1l : entity.getApplication().getId()),
            (entity.getComponent() == null ? -1l : entity.getComponent().getId()),
            (entity.getComponentInstance() == null ? -1l : entity.getComponentInstance().getId()),
            (entity.getCloud() == null ? -1l : entity.getCloud().getId()),
            entity);
    }

    @Override @Transactional protected void postPut(RawMonitor entity) {
        super.postPut(entity);

        se.updateMonitor(entity);
    }

    @Override @Transactional public Result delete(final Long id){
        se.removeMonitor(id); // moved here because access to monitor is still needed

        //TODO implement postDelete with true/false if delete worked
        Result parent = super.delete(id);

        // TODO now done in scaling engine: remove after integration
//        for(MonitorInstance mi : monitorInstanceModelService.getAll()){
//            if(mi.getMonitor().getId().equals(id)){
//                String ipaddress todo;
//                String port todo;
//
//                AgentCommunicator ac = AgentCommunicator.getCommunicator("http", ipaddress, port);
//                Monitor coolMonitor = Monitor.builder().
//                    sensorClassName(getModelService().getById(id).getSensorDescription().getClassName()).
//                    metricName(getModelService().getById(id).getSensorDescription().getMetricName()).
//                    interval(getModelService().getById(id).getSchedule().getInterval(), getModelService().getById(id).getSchedule().getTimeUnit()).
//                    build();
//                ac.removeMonitor(coolMonitor);
//                monitorInstanceModelService.delete(mi);
//            }
//        }

        return parent;
    }
}
