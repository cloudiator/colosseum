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

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.LocationDto;
import dtos.conversion.ModelDtoConversionService;
import models.CloudCredential;
import models.FrontendGroup;
import models.FrontendUser;
import models.Location;
import models.service.api.FrontendUserService;
import models.service.api.generic.ModelService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 09.04.15.
 */
public class LocationController
    extends GenericApiController<Location, LocationDto, LocationDto, LocationDto> {
    private final FrontendUserService frontendUserModelService;

    /**
     * Constructs a GenericApiController.
     *
     * @param modelService             the model service for retrieving the models.
     * @param typeLiteral              a type literal for the model type
     * @param conversionService        the conversion service for converting models and dtos.
     * @param frontendUserModelService service to retrieve frontend users from db.
     * @throws NullPointerException if any of the above parameters is null.
     */
    @Inject public LocationController(ModelService<Location> modelService,
        TypeLiteral<Location> typeLiteral, ModelDtoConversionService conversionService,
        FrontendUserService frontendUserModelService) {
        super(modelService, typeLiteral, conversionService);
        checkNotNull(frontendUserModelService);
        this.frontendUserModelService = frontendUserModelService;
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.LocationController.get(id).absoluteURL(request());
    }

    @Override protected Optional<Predicate<Location>> filter() {
        return Optional.of(new Predicate<Location>() {
            @Override public boolean apply(Location location) {
                String userName = request().username();

                FrontendUser frontendUser = frontendUserModelService.getByMail(userName);
                for (FrontendGroup frontendGroup : frontendUser.getFrontendGroups()) {
                    for (CloudCredential cloudCredential : frontendGroup.getCloudCredentials()) {
                        if (location.getCloudCredentials().contains(cloudCredential)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }
}
