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

package api.binding.binders;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import api.dto.ComponentHorizontalOutScalingActionDto;
import api.binding.AbstractModelToDtoBinding;
import api.binding.transformers.IdToModelTransformer;
import api.binding.transformers.StringToExternalReferenceTransformer;
import models.ApplicationComponent;
import models.ComponentHorizontalOutScalingAction;
import models.generic.ExternalReference;
import models.service.ModelService;

import java.util.List;


@Singleton public class ComponentHorizontalOutScalingActionModelToDtoBinding extends
    AbstractModelToDtoBinding<ComponentHorizontalOutScalingAction, ComponentHorizontalOutScalingActionDto> {

    private final ModelService<ApplicationComponent> applicationComponentModelService;
    private final ModelService<ExternalReference> externalReferenceModelService;

    @Inject protected ComponentHorizontalOutScalingActionModelToDtoBinding(
        ModelService<ApplicationComponent> applicationComponentModelService,
        ModelService<ExternalReference> externalReferenceModelService) {
        super(ComponentHorizontalOutScalingAction.class,
            ComponentHorizontalOutScalingActionDto.class);
        this.applicationComponentModelService = applicationComponentModelService;
        this.externalReferenceModelService = externalReferenceModelService;
    }

    @Override public void configure() {
        binding().fromField("amount").toField("amount");
        binding().fromField("min").toField("min");
        binding().fromField("max").toField("max");
        binding().fromField("count").toField("count");
        binding(Long.class, ApplicationComponent.class).fromField("applicationComponent").toField("applicationComponent")
                .withTransformation(new IdToModelTransformer<>(applicationComponentModelService));
        binding(new TypeLiteral<List<String>>() {
        }, new TypeLiteral<List<ExternalReference>>() {
        }).
                fromField("externalReferences").
                toField("externalReferences")
                .withTransformation(
                        new StringToExternalReferenceTransformer());
    }
}
