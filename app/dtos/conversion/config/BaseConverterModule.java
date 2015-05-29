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

package dtos.conversion.config;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import dtos.conversion.*;
import dtos.conversion.api.DtoConverter;
import models.SensorDescription;

/**
 * Base converter module.
 * <p>
 * Registers the basic converters.
 */
public class BaseConverterModule extends AbstractModule {

    @Override protected void configure() {
        Multibinder<DtoConverter> converterBinder =
            Multibinder.newSetBinder(binder(), DtoConverter.class);
        // API
        converterBinder.addBinding().to(ApiConverter.class);
        // Application
        converterBinder.addBinding().to(ApplicationConverter.class);
        // Application Component
        converterBinder.addBinding().to(ApplicationComponentConverter.class);
        // Application Instance
        converterBinder.addBinding().to(ApplicationInstanceConverter.class);
        // Cloud
        converterBinder.addBinding().to(CloudConverter.class);
        // Cloud Credential
        converterBinder.addBinding().to(CloudCredentialConverter.class);
        // Communication
        converterBinder.addBinding().to(CommunicationConverter.class);
        // Communication Channel
        converterBinder.addBinding().to(CommunicationChannelConverter.class);
        // ComponentHorizontalOutScalingAction
        converterBinder.addBinding().to(ComponentHorizontalOutScalingActionConverter.class);
        // ComposedMonitor
        converterBinder.addBinding().to(ComposedMonitorConverter.class);
        // ConstantMonitor
        converterBinder.addBinding().to(ConstantMonitorConverter.class);
        // FormulaQuantifier
        converterBinder.addBinding().to(FormulaQuantifierConverter.class);
        // Frontend Group
        converterBinder.addBinding().to(FrontendGroupConverter.class);
        //Frontend User
        converterBinder.addBinding().to(FrontendUserConverter.class);
        //GeoLocation
        converterBinder.addBinding().to(GeoLocationConverter.class);
        // Hardware
        converterBinder.addBinding().to(HardwareConverter.class);
        //HardwareOffer
        converterBinder.addBinding().to(HardwareOfferConverter.class);
        // Image
        converterBinder.addBinding().to(ImageConverter.class);
        // Instance
        converterBinder.addBinding().to(InstanceConverter.class);
        // IpAddress
        converterBinder.addBinding().to(IpAddressConverter.class);
        // Lifecycle Component
        converterBinder.addBinding().to(LifecycleComponentConverter.class);
        // Location
        converterBinder.addBinding().to(LocationConverter.class);
        // MonitorInstance
        converterBinder.addBinding().to(MonitorInstanceConverter.class);
        //Operating System
        converterBinder.addBinding().to(OperatingSystemConverter.class);
        //Operating System Vendor
        converterBinder.addBinding().to(OperatingSystemVendorConverter.class);
        // RawMonitor
        converterBinder.addBinding().to(RawMonitorConverter.class);
        // Schedule
        converterBinder.addBinding().to(ScheduleConverter.class);
        // SensorDescription
        converterBinder.addBinding().to(SensorDescriptionConverter.class);
        // TimeWindow
        converterBinder.addBinding().to(TimeWindowConverter.class);
        // Virtual Machine
        converterBinder.addBinding().to(VirtualMachineConverter.class);
        // Virtual Machine Template
        converterBinder.addBinding().to(VirtualMachineTemplateConverter.class);
    }

}
