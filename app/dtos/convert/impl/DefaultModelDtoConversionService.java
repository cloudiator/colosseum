/*
 * Copyright (c) 2015 University of Ulm
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

package dtos.convert.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dtos.convert.api.ConverterRegistry;
import dtos.convert.converters.api.ModelDtoConverter;
import play.Logger;

import java.util.Set;

/**
 * A generic implementation of the ModelDtoConversionService, which automatically
 * registers the dependency injected converts within the registry.
 *
 * @author Daniel Baur
 */
@Singleton
public class DefaultModelDtoConversionService extends GenericModelDtoConversionService {

    /**
     * Constructor.
     *
     * @param converters a set of dependency injected converters.
     */
    @Inject
    public DefaultModelDtoConversionService(Set<ModelDtoConverter> converters) {
        addDefaultConverters(this, converters);
    }

    /**
     * Registers the dependency injected converters within the registry.
     *
     * @param converterRegistry the registry where the converters should be registered.
     * @param converters        the converts that should be registered.
     */
    private void addDefaultConverters(ConverterRegistry converterRegistry, Set<ModelDtoConverter> converters) {
        Logger.debug("Registering default dto converters.");
        converters.forEach(converterRegistry::addConverter);
    }
}
