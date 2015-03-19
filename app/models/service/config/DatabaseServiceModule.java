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

package models.service.config;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import dtos.LoginDto;
import models.Cloud;
import models.service.api.ApiAccessTokenService;
import models.service.api.FrontendUserService;
import models.service.api.generic.ModelService;
import models.service.impl.DefaultApiAccessTokenService;
import models.service.impl.DefaultFrontendUserService;
import models.service.impl.generic.BaseModelService;

/**
 * Created by daniel on 18.03.15.
 */
public class DatabaseServiceModule extends AbstractModule {
    @Override protected void configure() {
        //API Access Token
        bind(ApiAccessTokenService.class).to(DefaultApiAccessTokenService.class);
        // Cloud
        bind(new TypeLiteral<ModelService<Cloud>>() {
        }).to(new TypeLiteral<BaseModelService<Cloud>>() {
        });
        // Frontend User
        bind(FrontendUserService.class).to(DefaultFrontendUserService.class);
        requestStaticInjection(LoginDto.References.class);
    }
}
