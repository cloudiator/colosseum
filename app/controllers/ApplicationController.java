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

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import components.model.ApplicationTypeGraph;
import controllers.generic.GenericApiController;
import dtos.ApplicationDto;
import dtos.conversion.ModelDtoConversionService;
import models.Application;
import models.Tenant;
import models.service.FrontendUserService;
import models.service.ModelService;
import play.db.jpa.Transactional;
import play.mvc.Result;

/**
 * Created by daniel on 29.03.15.
 */
public class ApplicationController
    extends GenericApiController<Application, ApplicationDto, ApplicationDto, ApplicationDto> {

    @Inject public ApplicationController(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService, ModelService<Application> modelService,
        TypeLiteral<Application> typeLiteral, ModelDtoConversionService conversionService) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
            conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.ApplicationController.get(id).absoluteURL(request());
    }

    @Transactional(readOnly = true) public Result graph(Long id) {
        Application application = loadEntity(id);

        if (application == null) {
            return notFound(id);
        }

        final ApplicationTypeGraph applicationTypeGraph = ApplicationTypeGraph.of(application);

        return ok(applicationTypeGraph.toJson());
    }

    @Transactional(readOnly = true) public Result display(Long id) {
        Application application = loadEntity(id);

        if (application == null) {
            return notFound(id);
        }

        return ok(views.html.graph.render(application));
    }

}
