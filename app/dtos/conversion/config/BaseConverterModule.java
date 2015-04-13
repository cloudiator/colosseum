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
        // Cloud
        converterBinder.addBinding().to(CloudConverter.class);
        // FrontendGroup
        converterBinder.addBinding().to(FrontendGroupConverter.class);
        //FrontendUSer
        converterBinder.addBinding().to(FrontendUserConverter.class);
    }

}
