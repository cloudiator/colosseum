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
import dtos.*;
import models.*;
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
        requestStaticInjection(ApplicationComponentDto.References.class);
        // Cloud
        bind(new TypeLiteral<ModelService<Cloud>>() {
        }).to(new TypeLiteral<BaseModelService<Cloud>>() {
        });
        // Cloud API
        bind(new TypeLiteral<ModelService<CloudApi>>() {
        }).to(new TypeLiteral<BaseModelService<CloudApi>>() {
        });
        requestStaticInjection(CloudApiDto.References.class);
        // Cloud Credential
        bind(new TypeLiteral<ModelService<CloudCredential>>() {
        }).to(new TypeLiteral<BaseModelService<CloudCredential>>() {
        });
        requestStaticInjection(CloudCredentialDto.References.class);
        // Communication
        bind(new TypeLiteral<ModelService<Communication>>() {
        }).to(new TypeLiteral<BaseModelService<Communication>>() {
        });
        requestStaticInjection(CommunicationDto.References.class);
        // Communication Channel
        bind(new TypeLiteral<ModelService<CommunicationChannel>>() {
        }).to(new TypeLiteral<BaseModelService<CommunicationChannel>>() {
        });
        requestStaticInjection(CommunicationChannelDto.References.class);
        // Component
        bind(new TypeLiteral<ModelService<Component>>() {
        }).to(new TypeLiteral<BaseModelService<Component>>() {
        });
        // Frontend User
        bind(FrontendUserService.class).to(DefaultFrontendUserService.class);
        bind(new TypeLiteral<ModelService<FrontendUser>>() {
        }).to(new TypeLiteral<BaseModelService<FrontendUser>>() {
        });
        requestStaticInjection(LoginDto.References.class);
        // Frontend User Group
        bind(new TypeLiteral<ModelService<FrontendGroup>>() {
        }).to(new TypeLiteral<BaseModelService<FrontendGroup>>() {
        });
        requestStaticInjection(FrontendGroupDto.References.class);
        //Geo Location
        bind(new TypeLiteral<ModelService<GeoLocation>>() {
        }).to(new TypeLiteral<BaseModelService<GeoLocation>>() {
        });
        //Hardware
        bind(new TypeLiteral<ModelService<Hardware>>() {
        }).to(new TypeLiteral<BaseModelService<Hardware>>() {
        });
        requestStaticInjection(HardwareDto.References.class);
        //Hardware Offer
        bind(new TypeLiteral<ModelService<HardwareOffer>>() {
        }).to(new TypeLiteral<BaseModelService<HardwareOffer>>() {
        });
        //Image
        bind(new TypeLiteral<ModelService<Image>>() {
        }).to(new TypeLiteral<BaseModelService<Image>>() {
        });
        requestStaticInjection(ImageDto.References.class);
        //Image Offer
        bind(new TypeLiteral<ModelService<ImageOffer>>() {
        }).to(new TypeLiteral<BaseModelService<ImageOffer>>() {
        });
        //Instance
        bind(new TypeLiteral<ModelService<Instance>>() {
        }).to(new TypeLiteral<BaseModelService<Instance>>() {
        });
        requestStaticInjection(InstanceDto.References.class);
        //Ip Address
        bind(new TypeLiteral<ModelService<IpAddress>>() {
        }).to(new TypeLiteral<BaseModelService<IpAddress>>() {
        });
        requestStaticInjection(IpAddressDto.References.class);
        //Lifecycle Component
        bind(new TypeLiteral<ModelService<LifecycleComponent>>() {
        }).to(new TypeLiteral<BaseModelService<LifecycleComponent>>() {
        });
        //Location
        bind(new TypeLiteral<ModelService<Location>>() {
        }).to(new TypeLiteral<BaseModelService<Location>>() {
        });
        requestStaticInjection(LocationDto.References.class);
        //Location Offer
        bind(new TypeLiteral<ModelService<LocationOffer>>() {
        }).to(new TypeLiteral<BaseModelService<LocationOffer>>() {
        });
        requestStaticInjection(LocationDto.References.class);
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
        requestStaticInjection(VirtualMachineDto.References.class);
        //VirtualMachineTemplate
        bind(new TypeLiteral<ModelService<VirtualMachineTemplate>>() {
        }).to(new TypeLiteral<BaseModelService<VirtualMachineTemplate>>() {
        });
        requestStaticInjection(VirtualMachineDto.References.class);
    }
}
