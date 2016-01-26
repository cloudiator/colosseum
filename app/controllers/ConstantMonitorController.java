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
import controllers.internal.GenericApiController;
import api.dto.ConstantMonitorDto;
import api.binding.BindingService;
import models.ConstantMonitor;
import models.Tenant;
import models.service.FrontendUserService;
import models.service.ModelService;

/**
 * Implementation of the GenericApiController for the ConstantMonitor model class.
 *
 * @author Daniel Baur
 */
public class ConstantMonitorController extends
    GenericApiController<ConstantMonitor, ConstantMonitorDto, ConstantMonitorDto, ConstantMonitorDto> {

    /**
     * Constructs a GenericApiController.
     *
     * @param modelService      the model service for retrieving the models.
     * @param typeLiteral       a type literal for the model type
     * @param conversionService the conversion service for converting models and dtos.
     * @throws NullPointerException if any of the above parameters is null.
     */
    @Inject public ConstantMonitorController(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService, ModelService<ConstantMonitor> modelService,
        TypeLiteral<ConstantMonitor> typeLiteral, BindingService conversionService) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral, conversionService);
    }
}
