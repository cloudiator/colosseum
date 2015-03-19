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

package models.repository.config;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import models.Cloud;
import models.repository.api.ApiAccessTokenRepository;
import models.repository.api.FrontendUserRepository;
import models.repository.api.generic.ModelRepository;
import models.repository.impl.ApiAccessTokenRepositoryJpa;
import models.repository.impl.FrontendUserRepositoryJpa;
import models.repository.impl.generic.BaseModelRepositoryJpa;

/**
 * Created by daniel on 18.03.15.
 */
public class JPAModule extends AbstractModule {

    @Override protected void configure() {
        // API Access Token
        bind(ApiAccessTokenRepository.class).to(ApiAccessTokenRepositoryJpa.class);
        // Cloud
        bind(new TypeLiteral<ModelRepository<Cloud>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Cloud>>() {
        });
        // Frontend User
        bind(FrontendUserRepository.class).to(FrontendUserRepositoryJpa.class);

    }
}
