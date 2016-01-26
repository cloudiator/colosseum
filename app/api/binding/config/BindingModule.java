/*
 * Copyright (c) 2014-2016 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package api.binding.config;

import api.binding.ModelToDtoBinding;
import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import play.Configuration;
import play.Play;

import java.lang.reflect.Modifier;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

/**
 * Base converter module.
 * <p>
 * Registers the basic converters.
 */
public class BindingModule extends AbstractModule {

    private final Configuration configuration;
    private final ClassLoader classLoader;

    public BindingModule(ClassLoader classLoader) {
        this.configuration = Play.application().configuration();
        this.classLoader = classLoader;
    }

    /**
     * Uses reflections (org.reflections) to load all classes implementing
     * a dto-model binding.
     * <p>
     * Note: to be able to resolve the transitive dependency DtoConverter - AbstractConverter - Concrete Converter
     * we first need to include the complete conversion package.
     * <p>
     * Afterwards we filter the found converters to only find concrete ones....
     */
    @Override protected void configure() {
        Multibinder<ModelToDtoBinding> bindingBinder = Multibinder.newSetBinder(binder(), ModelToDtoBinding.class);

        for (Class<? extends ModelToDtoBinding> bindingClass : Sets
            .filter(findBindings(), new ClassFilter())) {
            bindingBinder.addBinding().to(bindingClass);
        }
    }

    private Set<Class<? extends ModelToDtoBinding>> findBindings() {

        String bindingPackage = configuration.getString("colosseum.binding.package");
        String bindersPackage = configuration.getString("colosseum.binding.binders");

        checkState(bindingPackage != null && bindersPackage != null,
            "Could not load binding package, please check your configuration.");

        Reflections reflections = new Reflections(
            new ConfigurationBuilder().addClassLoader(classLoader)
                .setUrls(ClasspathHelper.forPackage(bindingPackage))
                .addScanners(new SubTypesScanner()));

        return Sets.filter(reflections.getSubTypesOf(ModelToDtoBinding.class),
            aClass -> aClass.getName().startsWith(bindersPackage));
    }

    private static class ClassFilter implements Predicate<Class<? extends ModelToDtoBinding>> {

        @Override public boolean apply(Class<? extends ModelToDtoBinding> aClass) {
            //filter abstract classes
            return !Modifier.isAbstract(aClass.getModifiers());
        }
    }

}
