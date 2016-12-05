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
        // Cloud Property
        bind(new TypeLiteral<ModelRepository<CloudProperty>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<CloudProperty>>() {
        });
        // Communication
        bind(new TypeLiteral<ModelRepository<Communication>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Communication>>() {
        });
        // Component
        bind(new TypeLiteral<ModelRepository<Component>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Component>>() {
        });
        //ComponentHorizontalOutScalingAction
        bind(new TypeLiteral<ModelRepository<ComponentHorizontalOutScalingAction>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<ComponentHorizontalOutScalingAction>>() {
        });
        //ComponentHorizontalIntScalingAction
        bind(new TypeLiteral<ModelRepository<ComponentHorizontalInScalingAction>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<ComponentHorizontalInScalingAction>>() {
        });
        //ComposedMonitor
        bind(new TypeLiteral<ModelRepository<ComposedMonitor>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<ComposedMonitor>>() {
        });
        //ConstantMonitor
        bind(new TypeLiteral<ModelRepository<ConstantMonitor>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<ConstantMonitor>>() {
        });
        //FormulaQuantifier
        bind(new TypeLiteral<ModelRepository<FormulaQuantifier>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<FormulaQuantifier>>() {
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
        bind(new TypeLiteral<RemoteResourceRepository<Image>>() {
        }).to(new TypeLiteral<BaseRemoteResourceRepositoryJpa<Image>>() {
        });
        //Instance
        bind(new TypeLiteral<ModelRepository<Instance>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Instance>>() {
        });
        bind(new TypeLiteral<RemoteResourceRepository<Instance>>() {
        }).to(new TypeLiteral<BaseRemoteResourceRepositoryJpa<Instance>>() {
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
        //MeasurementWindow
        bind(new TypeLiteral<ModelRepository<MeasurementWindow>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<MeasurementWindow>>() {
        });
        //Monitor
        bind(new TypeLiteral<ModelRepository<Monitor>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Monitor>>() {
        });
        //MonitorInstance
        bind(new TypeLiteral<ModelRepository<MonitorInstance>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<MonitorInstance>>() {
        });
        //MonitorSubscription
        bind(new TypeLiteral<ModelRepository<MonitorSubscription>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<MonitorSubscription>>() {
        });
        bind(new TypeLiteral<RemoteResourceRepository<Location>>() {
        }).to(new TypeLiteral<BaseRemoteResourceRepositoryJpa<Location>>() {
        });
        //Operating System
        bind(new TypeLiteral<ModelRepository<OperatingSystem>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<OperatingSystem>>() {
        });
        //RawMonitor
        bind(new TypeLiteral<ModelRepository<RawMonitor>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<RawMonitor>>() {
        });
        //ScalingAction
        bind(new TypeLiteral<ModelRepository<ScalingAction>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<ScalingAction>>() {
        });
        //Schedule
        bind(new TypeLiteral<ModelRepository<Schedule>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Schedule>>() {
        });
        //SensorConfigurations
        bind(new TypeLiteral<ModelRepository<SensorConfigurations>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<SensorConfigurations>>() {
        });
        //SensorDescription
        bind(new TypeLiteral<ModelRepository<SensorDescription>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<SensorDescription>>() {
        });
        //TemplateOptions
        bind(new TypeLiteral<ModelRepository<TemplateOptions>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<TemplateOptions>>() {
        });
        //TimeWindow
        bind(new TypeLiteral<ModelRepository<TimeWindow>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<TimeWindow>>() {
        });
        //PortInbound
        bind(new TypeLiteral<ModelRepository<PortRequired>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<PortRequired>>() {
        });
        //PortOutbound
        bind(new TypeLiteral<ModelRepository<PortProvided>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<PortProvided>>() {
        });
        //VirtualMachine
        bind(new TypeLiteral<ModelRepository<VirtualMachine>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<VirtualMachine>>() {
        });
        bind(new TypeLiteral<RemoteResourceRepository<VirtualMachine>>() {
        }).to(new TypeLiteral<BaseRemoteResourceRepositoryJpa<VirtualMachine>>() {
        });
        //VirtualMachineTemplate
        bind(new TypeLiteral<ModelRepository<VirtualMachineTemplate>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<VirtualMachineTemplate>>() {
        });
        //Window
        bind(new TypeLiteral<ModelRepository<Window>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Window>>() {
        });
        //PaaS Extension
        bind(new TypeLiteral<ModelRepository<PlatformApi>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<PlatformApi>>() {
        });
        bind(new TypeLiteral<ModelRepository<Platform>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<Platform>>() {
        });
        bind(new TypeLiteral<ModelRepository<PlatformComponent>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<PlatformComponent>>() {
        });
        bind(new TypeLiteral<ModelRepository<PlatformHardware>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<PlatformHardware>>() {
        });
        bind(new TypeLiteral<ModelRepository<PlatformCredential>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<PlatformCredential>>() {
        });
        bind(new TypeLiteral<ModelRepository<PlatformRuntime>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<PlatformRuntime>>() {
        });
        bind(new TypeLiteral<ModelRepository<PlatformEnvironment>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<PlatformEnvironment>>() {
        });
        bind(new TypeLiteral<ModelRepository<PlatformEnvironmentTemplate>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<PlatformEnvironmentTemplate>>() {
        });
        bind(new TypeLiteral<ModelRepository<PlatformInstance>>() {
        }).to(new TypeLiteral<BaseModelRepositoryJpa<PlatformInstance>>() {
        });
        bind(new TypeLiteral<RemoteResourceRepository<PlatformInstance>>() {
        }).to(new TypeLiteral<BaseRemoteResourceRepositoryJpa<PlatformInstance>>() {
        });
    }
}
