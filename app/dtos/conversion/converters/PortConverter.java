/*
 * Copyright (c) 2014-2015 University of Ulm
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

package dtos.conversion.converters;

import com.google.inject.Inject;

import dtos.PortDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import de.uniulm.omi.cloudiator.persistance.entities.ApplicationComponent;
import de.uniulm.omi.cloudiator.persistance.entities.Port;
import de.uniulm.omi.cloudiator.persistance.repositories.ModelService;

/**
 * Created by daniel on 03.08.15.
 */
public abstract class PortConverter<T extends Port, S extends PortDto>
    extends AbstractConverter<T, S> {

    private final ModelService<ApplicationComponent> applicationComponentModelService;

    @Inject public PortConverter(Class<T> t, Class<S> s,
        ModelService<ApplicationComponent> applicationComponentModelService) {
        super(t, s);
        this.applicationComponentModelService = applicationComponentModelService;
    }

    @Override public void configure() {
        binding().fromField("name").toField("name");
        binding(Long.class, ApplicationComponent.class).fromField("applicationComponent")
            .toField("applicationComponent")
            .withTransformation(new IdToModelTransformer<>(applicationComponentModelService));
    }
}
