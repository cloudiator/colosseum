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

package api.binding.binders;

import com.google.inject.Inject;
import api.dto.PortDto;
import api.binding.AbstractModelToDtoBinding;
import api.binding.transformers.IdToModelTransformer;
import models.ApplicationComponent;
import models.Port;
import models.service.ModelService;

/**
 * Created by daniel on 03.08.15.
 */
public abstract class PortModelToDtoBinding<T extends Port, S extends PortDto>
    extends AbstractModelToDtoBinding<T, S> {

    private final ModelService<ApplicationComponent> applicationComponentModelService;

    @Inject public PortModelToDtoBinding(Class<T> t, Class<S> s,
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
