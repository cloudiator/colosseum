/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package controllers;

import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.PortInboundDto;
import dtos.conversion.ModelDtoConversionService;
import models.PortInbound;
import models.Tenant;
import models.service.FrontendUserService;
import models.service.ModelService;

/**
 * Created by daniel on 03.08.15.
 */
public class PortInboundController
    extends GenericApiController<PortInbound, PortInboundDto, PortInboundDto, PortInboundDto> {

    public PortInboundController(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService, ModelService<PortInbound> modelService,
        TypeLiteral<PortInbound> typeLiteral, ModelDtoConversionService conversionService) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
            conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.PortInboundController.get(id).absoluteURL(request());
    }
}
