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

package dtos.conversion;

import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

/**
 * Base converter module.
 * <p>
 * Registers the basic converters.
 */
public class BaseConverterModule extends AbstractModule {

    private final ClassLoader classLoader;

    public BaseConverterModule(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * Uses reflections (org.reflections) to load all classes implementing
     * a dto converter.
     * <p>
     * Note: to be able to resolve the transitive dependency DtoConverter - AbstractConverter - Concrete Converter
     * we first need to include the complete conversion package.
     * <p>
     * Afterwards we filter the found converters to only find concrete ones....
     *
     * @todo make packages configurable?
     */
    @Override protected void configure() {
        Multibinder<DtoConverter> converterBinder =
            Multibinder.newSetBinder(binder(), DtoConverter.class);
        //todo make package configurable?
        Reflections reflections = new Reflections(
            new ConfigurationBuilder().addClassLoader(classLoader)
                .setUrls(ClasspathHelper.forPackage("dtos.conversion"))
                .addScanners(new SubTypesScanner()));

        final Set<Class<? extends DtoConverter>> converters =
            Sets.filter(reflections.getSubTypesOf(DtoConverter.class),
                aClass -> aClass.getName().startsWith("dtos.conversion.converters"));

        for (Class<? extends DtoConverter> converter : converters) {
            converterBinder.addBinding().to(converter);
        }
    }
}
