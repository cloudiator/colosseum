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
import components.scalability.ScalingEngine;
import controllers.generic.GenericApiController;
import dtos.ComposedMonitorDto;
import dtos.conversion.ModelDtoConversionService;
import models.ComposedMonitor;
import models.Tenant;
import models.service.FrontendUserService;
import models.service.ModelService;
import play.db.jpa.Transactional;
import play.mvc.Result;

/**
 * Implementation of the GenericApiController for the ComposedMonitor model class.
 *
 * @author Daniel Baur
 */
public class ComposedMonitorController extends
    GenericApiController<ComposedMonitor, ComposedMonitorDto, ComposedMonitorDto, ComposedMonitorDto> {

    private ScalingEngine se;

    /**
     * Constructs a GenericApiController.
     *
     * @param modelService      the model service for retrieving the models.
     * @param typeLiteral       a type literal for the model type
     * @param conversionService the conversion service for converting models and dtos.
     * @throws NullPointerException if any of the above parameters is null.
     */
    @Inject public ComposedMonitorController(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService, ModelService<ComposedMonitor> modelService,
        TypeLiteral<ComposedMonitor> typeLiteral, ModelDtoConversionService conversionService,
        ScalingEngine se) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral, conversionService);

        this.se = se;
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.ComposedMonitorController.get(id).absoluteURL(request());
    }

    @Override @Transactional protected void postPost(ComposedMonitor entity) {
        super.postPost(entity);

        se.aggregateMonitors(entity, true);
    }

    @Override @Transactional protected void postPut(ComposedMonitor entity) {
        super.postPut(entity);

        //TODO better update than remove and add?
        se.removeMonitor(entity.getId());

        se.aggregateMonitors(entity, true);
    }

    @Override @Transactional public Result delete(final Long id){
        se.removeMonitor(id); // moved here because access to monitor is still needed

        Result parent = super.delete(id);

        return parent;
    }
}
