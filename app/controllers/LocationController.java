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
import controllers.generic.GenericApiController;
import dtos.LocationDto;
import dtos.conversion.ModelDtoConversionService;
import models.CloudCredential;
import models.Location;
import models.Tenant;
import models.service.FrontendUserService;
import models.service.ModelService;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by daniel on 09.04.15.
 */
public class LocationController
    extends GenericApiController<Location, LocationDto, LocationDto, LocationDto> {

    @Inject public LocationController(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService, ModelService<Location> modelService,
        TypeLiteral<Location> typeLiteral, ModelDtoConversionService conversionService) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
            conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.LocationController.get(id).absoluteURL(request());
    }

    @Override protected Optional<Predicate<Location>> filter() {
        return Optional.of(location -> {
            for (Tenant tenant : getUser().getTenants()) {
                for (CloudCredential cloudCredential : tenant.getCloudCredentials()) {
                    if (location.cloudCredentials().contains(cloudCredential)) {
                        return true;
                    }
                }
            }
            return false;
        });
    }
}
