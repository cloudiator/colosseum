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
import dtos.MonitorSubscriptionDto;
import dtos.conversion.ModelDtoConversionService;
import models.MonitorSubscription;
import models.Tenant;
import models.scalability.SubscriptionType;
import models.service.FrontendUserService;
import models.service.ModelService;
import play.db.jpa.Transactional;
import play.mvc.Result;

/**
 * Created by Frank on 02.08.2015.
 */
public class MonitorSubscriptionController extends
    GenericApiController<MonitorSubscription, MonitorSubscriptionDto, MonitorSubscriptionDto, MonitorSubscriptionDto> {

    private ScalingEngine se;

    /**
     * Constructs a GenericApiController.
     *
     * @param modelService      the model service for retrieving the models.
     * @param typeLiteral       a type literal for the model type
     * @param conversionService the conversion service for converting models and dtos.
     * @throws NullPointerException if any of the above parameters is null.
     */
    @Inject public MonitorSubscriptionController(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService, ModelService<MonitorSubscription> modelService,
        TypeLiteral<MonitorSubscription> typeLiteral, ModelDtoConversionService conversionService,
        ScalingEngine se) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral, conversionService);

        this.se = se;
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.MonitorSubscriptionController.get(id).absoluteURL(request());
    }

    @Override @Transactional protected void postPost(MonitorSubscription entity) {
        super.postPost(entity);

        se.subscribe(entity.getMonitor(), entity);
    }

    @Override @Transactional protected void postPut(MonitorSubscription entity) {
        super.postPut(entity);

        se.unsubscribe(entity.getId());
        se.subscribe(entity.getMonitor(), entity);
    }

    @Override @Transactional public Result delete(final Long id) {
        // for each mi : se.unsubscribe(id); // moved here because access to monitor is still needed
        se.unsubscribe(id); //TODO currently misused monitor id here, since we dont know it anymore - preDelete?

        Result parent = super.delete(id);

        return parent;
    }
}
