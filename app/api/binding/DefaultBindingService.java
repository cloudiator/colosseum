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

package api.binding;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.Logger;

import java.util.Set;

/**
 * A generic implementation of the {@link BindingServiceWithRegistry}, which automatically
 * registers the dependency injected bindings within the registry.
 *
 * @author Daniel Baur
 */
@Singleton public class DefaultBindingService extends BaseBindingService {

    /**
     * Constructor.
     *
     * @param binders a set of dependency injected binders.
     */
    @Inject public DefaultBindingService(Set<ModelToDtoBinding> binders) {
        addDefaultConverters(this, binders);
    }

    /**
     * Registers the dependency injected binders within the registry.
     *
     * @param bindingRegistry the registry where the binders should be registered.
     * @param binders         the converts that should be registered.
     */
    private void addDefaultConverters(BindingRegistry bindingRegistry,
        Set<ModelToDtoBinding> binders) {
        Logger.debug("Registering default api binders.");
        binders.forEach(bindingRegistry::addBinding);
    }
}
