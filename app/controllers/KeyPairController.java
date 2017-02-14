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

import com.google.common.io.Files;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import controllers.generic.GenericApiController;
import dtos.KeyPairDto;
import dtos.conversion.ModelDtoConversionService;
import de.uniulm.omi.cloudiator.persistance.entities.KeyPair;
import de.uniulm.omi.cloudiator.persistance.entities.Tenant;
import de.uniulm.omi.cloudiator.persistance.repositories.FrontendUserService;
import de.uniulm.omi.cloudiator.persistance.repositories.ModelService;
import play.db.jpa.Transactional;
import play.mvc.Result;

/**
 * Created by daniel on 19.05.15.
 */
public class KeyPairController
    extends GenericApiController<KeyPair, KeyPairDto, KeyPairDto, KeyPairDto> {

    @Inject public KeyPairController(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService, ModelService<KeyPair> modelService,
        TypeLiteral<KeyPair> typeLiteral, ModelDtoConversionService conversionService) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
            conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.KeyPairController.get(id).absoluteURL(request());
    }

    @Transactional(readOnly = true)
    public Result download(Long id) {
        KeyPair keyPair = loadEntity(id);

        if (keyPair == null) {
            return notFound(id);
        }

        //write temporary download file
        try {
            File file = File.createTempFile("id_rsa", keyPair.getUuid());
            Files.write(keyPair.getPrivateKey(), file, Charset.forName("UTF-8"));
            return ok(file);
        } catch (IOException e) {
            return internalServerError();
        }
    }
}
