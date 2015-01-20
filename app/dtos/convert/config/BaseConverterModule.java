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

package dtos.convert.config;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import dtos.convert.converters.api.ModelDtoConverter;
import dtos.convert.converters.impl.*;
import models.CloudImage;
import models.CloudLocation;
import models.LifecycleComponent;

/**
 * Base converter module.
 * <p>
 * Registers the basic converters.
 */
public class BaseConverterModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<ModelDtoConverter> converterBinder = Multibinder.newSetBinder(binder(), ModelDtoConverter.class);
        converterBinder.addBinding().to(CloudConverter.class);
        converterBinder.addBinding().to(VirtualMachineConverter.class);
        converterBinder.addBinding().to(CloudHardwareConverter.class);
        converterBinder.addBinding().to(CloudImageConverter.class);
        converterBinder.addBinding().to(CloudLocationConverter.class);
        converterBinder.addBinding().to(HardwareConverter.class);
        converterBinder.addBinding().to(ImageConverter.class);
        converterBinder.addBinding().to(CredentialConverter.class);
        converterBinder.addBinding().to(CloudApiConverter.class);
        converterBinder.addBinding().to(ApiConverter.class);
        converterBinder.addBinding().to(FrontendUserConverter.class);
        converterBinder.addBinding().to(UserCredentialConverter.class);
        converterBinder.addBinding().to(ApplicationConverter.class);
        converterBinder.addBinding().to(LifecycleComponentConverter.class);
        converterBinder.addBinding().to(ApplicationComponentConverter.class);
        converterBinder.addBinding().to(InstanceConverter.class);
        converterBinder.addBinding().to(CommunicationConverter.class);
        converterBinder.addBinding().to(CommunicationChannelConverter.class);
    }

}
