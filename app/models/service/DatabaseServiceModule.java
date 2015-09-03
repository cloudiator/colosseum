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

package models.service;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import models.*;
import models.generic.ExternalReference;

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
        //Application Instance
        bind(new TypeLiteral<ModelService<ApplicationInstance>>() {
        }).to(new TypeLiteral<BaseModelService<ApplicationInstance>>() {
        });
        // Cloud
        bind(new TypeLiteral<ModelService<Cloud>>() {
        }).to(new TypeLiteral<BaseModelService<Cloud>>() {
        });
        // Cloud Credential
        bind(new TypeLiteral<ModelService<CloudCredential>>() {
        }).to(new TypeLiteral<BaseModelService<CloudCredential>>() {
        });
        bind(CloudCredentialModelService.class).to(DefaultCloudCredentialModelService.class);
        // Communication
        bind(new TypeLiteral<ModelService<Communication>>() {
        }).to(new TypeLiteral<BaseModelService<Communication>>() {
        });
        //ComponentHorizontalOutScalingAction
        bind(new TypeLiteral<ModelService<ComponentHorizontalOutScalingAction>>() {
        }).to(new TypeLiteral<BaseModelService<ComponentHorizontalOutScalingAction>>() {
        });
        //ComposedMonitor
        bind(new TypeLiteral<ModelService<ComposedMonitor>>() {
        }).to(new TypeLiteral<BaseModelService<ComposedMonitor>>() {
        });
        //ConstantMonitor
        bind(new TypeLiteral<ModelService<ConstantMonitor>>() {
        }).to(new TypeLiteral<BaseModelService<ConstantMonitor>>() {
        });
        //FormulaQuantifier
        bind(new TypeLiteral<ModelService<FormulaQuantifier>>() {
        }).to(new TypeLiteral<BaseModelService<FormulaQuantifier>>() {
        });
        // Component
        bind(new TypeLiteral<ModelService<Component>>() {
        }).to(new TypeLiteral<BaseModelService<Component>>() {
        });
        // ExternalReference
        bind(new TypeLiteral<ModelService<ExternalReference>>() {
        }).to(new TypeLiteral<BaseModelService<ExternalReference>>() {
        });
        // Frontend User
        bind(FrontendUserService.class).to(DefaultFrontendUserService.class);
        bind(new TypeLiteral<ModelService<FrontendUser>>() {
        }).to(new TypeLiteral<BaseModelService<FrontendUser>>() {
        });
        // Frontend User Group
        bind(new TypeLiteral<ModelService<Tenant>>() {
        }).to(new TypeLiteral<BaseModelService<Tenant>>() {
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
        //Key Pairs
        bind(new TypeLiteral<ModelService<KeyPair>>() {
        }).to(new TypeLiteral<BaseModelService<KeyPair>>() {
        });
        bind(KeyPairModelService.class).to(DefaultKeyPairModelService.class);
        //Lifecycle Component
        bind(new TypeLiteral<ModelService<LifecycleComponent>>() {
        }).to(new TypeLiteral<BaseModelService<LifecycleComponent>>() {
        });
        //Location
        bind(new TypeLiteral<ModelService<Location>>() {
        }).to(new TypeLiteral<BaseModelService<Location>>() {
        });
        //TimeWindow
        bind(new TypeLiteral<ModelService<MeasurementWindow>>() {
        }).to(new TypeLiteral<BaseModelService<MeasurementWindow>>() {
        });
        bind(LocationModelService.class).to(DefaultLocationModelService.class);
        //Monitor
        bind(new TypeLiteral<ModelService<Monitor>>() {
        }).to(new TypeLiteral<BaseModelService<Monitor>>() {
        });
        //MonitorInstance
        bind(new TypeLiteral<ModelService<MonitorInstance>>() {
        }).to(new TypeLiteral<BaseModelService<MonitorInstance>>() {
        });
        //MonitorSubscription
        bind(new TypeLiteral<ModelService<MonitorSubscription>>() {
        }).to(new TypeLiteral<BaseModelService<MonitorSubscription>>() {
        });
        //Operating System
        bind(new TypeLiteral<ModelService<OperatingSystem>>() {
        }).to(new TypeLiteral<BaseModelService<OperatingSystem>>() {
        });
        bind(OperatingSystemService.class).to(DefaultOperatingSystemService.class);
        //Operating System Vendor
        bind(new TypeLiteral<ModelService<OperatingSystemVendor>>() {
        }).to(new TypeLiteral<BaseModelService<OperatingSystemVendor>>() {
        });
        //RawMonitor
        bind(new TypeLiteral<ModelService<RawMonitor>>() {
        }).to(new TypeLiteral<BaseModelService<RawMonitor>>() {
        });
        //ScalingAction
        bind(new TypeLiteral<ModelService<ScalingAction>>() {
        }).to(new TypeLiteral<BaseModelService<ScalingAction>>() {
        });
        //Schedule
        bind(new TypeLiteral<ModelService<Schedule>>() {
        }).to(new TypeLiteral<BaseModelService<Schedule>>() {
        });
        //SensorDescription
        bind(new TypeLiteral<ModelService<SensorDescription>>() {
        }).to(new TypeLiteral<BaseModelService<SensorDescription>>() {
        });
        //TimeWindow
        bind(new TypeLiteral<ModelService<TimeWindow>>() {
        }).to(new TypeLiteral<BaseModelService<TimeWindow>>() {
        });
        //PortInbound
        bind(new TypeLiteral<ModelService<PortRequired>>() {
        }).to(new TypeLiteral<BaseModelService<PortRequired>>() {
        });
        //PortOutbound
        bind(new TypeLiteral<ModelService<PortProvided>>() {
        }).to(new TypeLiteral<BaseModelService<PortProvided>>() {
        });
        //VirtualMachine
        bind(new TypeLiteral<ModelService<VirtualMachine>>() {
        }).to(new TypeLiteral<BaseModelService<VirtualMachine>>() {
        });
        //VirtualMachineTemplate
        bind(new TypeLiteral<ModelService<VirtualMachineTemplate>>() {
        }).to(new TypeLiteral<BaseModelService<VirtualMachineTemplate>>() {
        });
        //Window
        bind(new TypeLiteral<ModelService<Window>>() {
        }).to(new TypeLiteral<BaseModelService<Window>>() {
        });
    }
}
