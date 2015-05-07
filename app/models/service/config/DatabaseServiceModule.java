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
import dtos.OperatingSystemDto;
import models.*;
import models.service.api.*;
import models.service.api.generic.ModelService;
import models.service.impl.*;
import models.service.impl.generic.BaseModelService;

/**
 * Created by daniel on 18.03.15.
 */
public class DatabaseServiceModule extends AbstractModule {

    @Override protected void configure() {

        install(new StaticServiceModule());

        // API
        bind(new TypeLiteral<ModelService<Api>>() {
        }).to(new TypeLiteral<BaseModelService<Api>>() {
        });
        //API Access Token
        bind(ApiAccessTokenService.class).to(DefaultApiAccessTokenService.class);
        //Application
        bind(new TypeLiteral<ModelService<Application>>() {
        }).to(new TypeLiteral<BaseModelService<Application>>() {
        });
        // Application Component
        bind(new TypeLiteral<ModelService<ApplicationComponent>>() {
        }).to(new TypeLiteral<BaseModelService<ApplicationComponent>>() {
        });
        // Cloud
        bind(new TypeLiteral<ModelService<Cloud>>() {
        }).to(new TypeLiteral<BaseModelService<Cloud>>() {
        });
        // Cloud API
        bind(new TypeLiteral<ModelService<CloudApi>>() {
        }).to(new TypeLiteral<BaseModelService<CloudApi>>() {
        });
        // Cloud Credential
        bind(new TypeLiteral<ModelService<CloudCredential>>() {
        }).to(new TypeLiteral<BaseModelService<CloudCredential>>() {
        });
        // Communication
        bind(new TypeLiteral<ModelService<Communication>>() {
        }).to(new TypeLiteral<BaseModelService<Communication>>() {
        });
        // Communication Channel
        bind(new TypeLiteral<ModelService<CommunicationChannel>>() {
        }).to(new TypeLiteral<BaseModelService<CommunicationChannel>>() {
        });
        // Component
        bind(new TypeLiteral<ModelService<Component>>() {
        }).to(new TypeLiteral<BaseModelService<Component>>() {
        });
        // Frontend User
        bind(FrontendUserService.class).to(DefaultFrontendUserService.class);
        bind(new TypeLiteral<ModelService<FrontendUser>>() {
        }).to(new TypeLiteral<BaseModelService<FrontendUser>>() {
        });
        // Frontend User Group
        bind(new TypeLiteral<ModelService<FrontendGroup>>() {
        }).to(new TypeLiteral<BaseModelService<FrontendGroup>>() {
        });
        //Geo Location
        bind(new TypeLiteral<ModelService<GeoLocation>>() {
        }).to(new TypeLiteral<BaseModelService<GeoLocation>>() {
        });
        //Hardware
        bind(new TypeLiteral<ModelService<Hardware>>() {
        }).to(new TypeLiteral<BaseModelService<Hardware>>() {
        });
        bind(HardwareModelService.class).to(DefaultHardwareModelService.class);
        //Hardware Offer
        bind(new TypeLiteral<ModelService<HardwareOffer>>() {
        }).to(new TypeLiteral<BaseModelService<HardwareOffer>>() {
        });
        //Image
        bind(new TypeLiteral<ModelService<Image>>() {
        }).to(new TypeLiteral<BaseModelService<Image>>() {
        });
        bind(ImageModelService.class).to(DefaultImageModelService.class);
        //Instance
        bind(new TypeLiteral<ModelService<Instance>>() {
        }).to(new TypeLiteral<BaseModelService<Instance>>() {
        });
        //Ip Address
        bind(new TypeLiteral<ModelService<IpAddress>>() {
        }).to(new TypeLiteral<BaseModelService<IpAddress>>() {
        });
        //Lifecycle Component
        bind(new TypeLiteral<ModelService<LifecycleComponent>>() {
        }).to(new TypeLiteral<BaseModelService<LifecycleComponent>>() {
        });
        //Location
        bind(new TypeLiteral<ModelService<Location>>() {
        }).to(new TypeLiteral<BaseModelService<Location>>() {
        });
        bind(LocationModelService.class).to(DefaultLocationModelService.class);
        //Operating System
        bind(new TypeLiteral<ModelService<OperatingSystem>>() {
        }).to(new TypeLiteral<BaseModelService<OperatingSystem>>() {
        });
        requestStaticInjection(OperatingSystemDto.References.class);
        //Operating System Vendor
        bind(new TypeLiteral<ModelService<OperatingSystemVendor>>() {
        }).to(new TypeLiteral<BaseModelService<OperatingSystemVendor>>() {
        });
        //VirtualMachine
        bind(new TypeLiteral<ModelService<VirtualMachine>>() {
        }).to(new TypeLiteral<BaseModelService<VirtualMachine>>() {
        });
        //VirtualMachineTemplate
        bind(new TypeLiteral<ModelService<VirtualMachineTemplate>>() {
        }).to(new TypeLiteral<BaseModelService<VirtualMachineTemplate>>() {
        });
    }
}
