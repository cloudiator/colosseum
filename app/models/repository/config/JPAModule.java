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
import models.*;
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
        // API
        bind(new TypeLiteral<ModelRepository<Api>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Api>>() {
        });
        // API Access Token
        bind(ApiAccessTokenRepository.class).to(ApiAccessTokenRepositoryJpa.class);
        // Application
        bind(new TypeLiteral<ModelRepository<Application>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Application>>() {
        });
        // Application Component
        bind(new TypeLiteral<ModelRepository<ApplicationComponent>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<ApplicationComponent>>() {
        });
        // Cloud
        bind(new TypeLiteral<ModelRepository<Cloud>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Cloud>>() {
        });
        // Cloud API
        bind(new TypeLiteral<ModelRepository<CloudApi>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<CloudApi>>() {
        });
        // Cloud Credential
        bind(new TypeLiteral<ModelRepository<CloudCredential>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<CloudCredential>>() {
        });
        // Communication
        bind(new TypeLiteral<ModelRepository<Communication>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Communication>>() {
        });
        // Communication Channel
        bind(new TypeLiteral<ModelRepository<CommunicationChannel>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<CommunicationChannel>>() {
        });
        // Component
        bind(new TypeLiteral<ModelRepository<Component>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Component>>() {
        });
        // Frontend User
        bind(FrontendUserRepository.class).to(FrontendUserRepositoryJpa.class);
        // Frontend User Group
        bind(new TypeLiteral<ModelRepository<FrontendGroup>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<FrontendGroup>>() {
        });
        //Geo Location
        bind(new TypeLiteral<ModelRepository<GeoLocation>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<GeoLocation>>() {
        });
        //Hardware
        bind(new TypeLiteral<ModelRepository<Hardware>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Hardware>>() {
        });
        //Hardware Properties
        bind(new TypeLiteral<ModelRepository<HardwareProperties>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<HardwareProperties>>() {
        });
        //Image
        bind(new TypeLiteral<ModelRepository<Image>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Image>>() {
        });
        //Image Properties
        bind(new TypeLiteral<ModelRepository<ImageProperties>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<ImageProperties>>() {
        });
        //Instance
        bind(new TypeLiteral<ModelRepository<Instance>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Instance>>() {
        });
        //Ip Address
        bind(new TypeLiteral<ModelRepository<IpAddress>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<IpAddress>>() {
        });
        //Lifecycle Component
        bind(new TypeLiteral<ModelRepository<LifecycleComponent>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<LifecycleComponent>>() {
        });
        //Location
        bind(new TypeLiteral<ModelRepository<Location>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Location>>() {
        });
        //LocationProperties
        bind(new TypeLiteral<ModelRepository<LocationProperties>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<LocationProperties>>() {
        });
        //VirtualMachine
        bind(new TypeLiteral<ModelRepository<VirtualMachine>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<VirtualMachine>>() {
        });
        //VirtualMachineTemplate
        bind(new TypeLiteral<ModelRepository<VirtualMachineTemplate>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<VirtualMachineTemplate>>() {
        });
    }
}
