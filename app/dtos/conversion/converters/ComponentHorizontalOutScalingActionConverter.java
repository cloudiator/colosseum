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

package dtos.conversion.converters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dtos.ComponentHorizontalOutScalingActionDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import dtos.conversion.transformers.StringToExternalReferenceTransformer;
import models.ApplicationComponent;
import models.ComponentHorizontalOutScalingAction;
import models.generic.ExternalReference;
import models.service.api.generic.ModelService;

import java.util.List;


@Singleton public class ComponentHorizontalOutScalingActionConverter extends
    AbstractConverter<ComponentHorizontalOutScalingAction, ComponentHorizontalOutScalingActionDto> {

    private final ModelService<ApplicationComponent> applicationComponentModelService;
    private final ModelService<ExternalReference> externalReferenceModelService;

    @Inject protected ComponentHorizontalOutScalingActionConverter(
        ModelService<ApplicationComponent> applicationComponentModelService,
        ModelService<ExternalReference> externalReferenceModelService) {
        super(ComponentHorizontalOutScalingAction.class,
            ComponentHorizontalOutScalingActionDto.class);
        this.applicationComponentModelService = applicationComponentModelService;
        this.externalReferenceModelService = externalReferenceModelService;
    }

    @Override public void configure() {
        builder().from("amount").to("amount");
        builder().from("min").to("min");
        builder().from("max").to("max");
        builder().from("count").to("count");
        builder().from(Long.class, "applicationComponent").to(ApplicationComponent.class, "applicationComponent")
            .withTransformation(new IdToModelTransformer<>(applicationComponentModelService));
        builder().from(List.class, "externalReferences").to(List.class, "externalReferences")
            .withUnsafeTransformation(
                new StringToExternalReferenceTransformer());
    }
}
