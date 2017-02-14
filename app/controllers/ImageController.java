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

import java.util.Optional;
import java.util.function.Predicate;

import controllers.generic.GenericApiController;
import dtos.ImageDto;
import dtos.conversion.ModelDtoConversionService;
import de.uniulm.omi.cloudiator.persistance.entities.CloudCredential;
import de.uniulm.omi.cloudiator.persistance.entities.Image;
import de.uniulm.omi.cloudiator.persistance.entities.Tenant;
import de.uniulm.omi.cloudiator.persistance.repositories.FrontendUserService;
import de.uniulm.omi.cloudiator.persistance.repositories.ModelService;

/**
 * Created by daniel on 09.04.15.
 */
public class ImageController extends GenericApiController<Image, ImageDto, ImageDto, ImageDto> {

    @Inject public ImageController(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService, ModelService<Image> modelService,
        TypeLiteral<Image> typeLiteral, ModelDtoConversionService conversionService) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
            conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.ImageController.get(id).absoluteURL(request());
    }

    @Override protected Optional<Predicate<Image>> filter() {
        return Optional.of(image -> {
            for (Tenant tenant : getUser().getTenants()) {
                for (CloudCredential cloudCredential : tenant.getCloudCredentials()) {
                    if (image.cloudCredentials().contains(cloudCredential)) {
                        return true;
                    }
                }
            }
            return false;
        });
    }
}
