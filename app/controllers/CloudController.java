/*
 * Copyright (c) 2014 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
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
import controllers.generic.GenericApiController;
import dtos.CloudDto;
import dtos.convert.api.ModelDtoConversionService;
import models.Cloud;
import models.service.api.CloudServiceInterface;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Implementation of the GenericApiController for the Cloud model class.
 *
 * @author Daniel Baur
 */
@Security.Authenticated(Secured.class)
public class CloudController extends GenericApiController<Cloud, CloudDto> {

    /**
     * Constructor.
     *
     * @param cloudService      Model service for cloud model.
     * @param conversionService Model <-> DTO conversion service.
     */
    @Inject
    protected CloudController(CloudServiceInterface cloudService, ModelDtoConversionService conversionService) {
        super(cloudService, conversionService);
    }

    public Result index() {
        return ok(views.html.cloud.index.render());
    }

    public Result form() {
        return ok(views.html.cloud.form.render());
    }

    @Override
    protected String getSelfRoute(Long id) {
        return controllers.routes.CloudController.get(id).absoluteURL(request());
    }
}
