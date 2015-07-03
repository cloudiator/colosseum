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
import models.repository.api.HardwareRepository;
import models.repository.api.generic.ModelRepository;
import models.repository.api.generic.RemoteModelRepository;
import models.repository.impl.ApiAccessTokenRepositoryJpa;
import models.repository.impl.FrontendUserRepositoryJpa;
import models.repository.impl.HardwareRepositoryJpa;
import models.repository.impl.generic.BaseModelRepositoryJpa;
import models.repository.impl.generic.BaseRemoteModelRepositoryJpa;

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
        //Application Instance
        bind(new TypeLiteral<ModelRepository<ApplicationInstance>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<ApplicationInstance>>() {
        });
        // Cloud
        bind(new TypeLiteral<ModelRepository<Cloud>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Cloud>>() {
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
        bind(new TypeLiteral<ModelRepository<FrontendUser>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<FrontendUser>>() {
        });
        // Frontend User Group
        bind(new TypeLiteral<ModelRepository<Tenant>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Tenant>>() {
        });
        //Geo Location
        bind(new TypeLiteral<ModelRepository<GeoLocation>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<GeoLocation>>() {
        });
        //Hardware
        bind(HardwareRepository.class).to(HardwareRepositoryJpa.class);
        bind(new TypeLiteral<ModelRepository<Hardware>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Hardware>>() {
        });
        //Hardware Properties
        bind(new TypeLiteral<ModelRepository<HardwareOffer>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<HardwareOffer>>() {
        });
        //Image
        bind(new TypeLiteral<ModelRepository<Image>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Image>>() {
        });
        bind(new TypeLiteral<RemoteModelRepository<Image>>() {
        }).to(new TypeLiteral<BaseRemoteModelRepositoryJpa<Image>>() {
        });
        //Instance
        bind(new TypeLiteral<ModelRepository<Instance>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Instance>>() {
        });
        //Ip Address
        bind(new TypeLiteral<ModelRepository<IpAddress>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<IpAddress>>() {
        });
        //KeyPair
        bind(new TypeLiteral<ModelRepository<KeyPair>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<KeyPair>>() {
        });
        //Lifecycle Component
        bind(new TypeLiteral<ModelRepository<LifecycleComponent>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<LifecycleComponent>>() {
        });
        //Location
        bind(new TypeLiteral<ModelRepository<Location>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Location>>() {
        });
        bind(new TypeLiteral<RemoteModelRepository<Location>>() {
        }).to(new TypeLiteral<BaseRemoteModelRepositoryJpa<Location>>() {
        });
        //Operating System
        bind(new TypeLiteral<ModelRepository<OperatingSystem>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<OperatingSystem>>() {
        });
        //Operating System Vendor
        bind(new TypeLiteral<ModelRepository<OperatingSystemVendor>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<OperatingSystemVendor>>() {
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
