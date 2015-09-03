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
import dtos.ApplicationComponentDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import models.Application;
import models.ApplicationComponent;
import models.Component;
import models.VirtualMachineTemplate;
import models.service.ModelService;

/**
 * Created by daniel on 10.04.15.
 */
public class ApplicationComponentConverter
    extends AbstractConverter<ApplicationComponent, ApplicationComponentDto> {

    private final ModelService<Application> applicationModelService;
    private final ModelService<Component> componentModelService;
    private final ModelService<models.VirtualMachineTemplate> virtualMachineTemplateModelService;

    @Inject
    protected ApplicationComponentConverter(ModelService<Application> applicationModelService,
        ModelService<Component> componentModelService,
        ModelService<models.VirtualMachineTemplate> virtualMachineTemplateModelService) {
        super(ApplicationComponent.class, ApplicationComponentDto.class);
        this.applicationModelService = applicationModelService;

        this.componentModelService = componentModelService;
        this.virtualMachineTemplateModelService = virtualMachineTemplateModelService;
    }

    @Override public void configure() {
        binding(Long.class, Application.class).fromField("application").toField("application")
            .withTransformation(new IdToModelTransformer<>(applicationModelService));
        binding(Long.class, Component.class).fromField("component").toField("component")
            .withTransformation(new IdToModelTransformer<>(componentModelService));
        binding(Long.class, VirtualMachineTemplate.class).fromField("virtualMachineTemplate")
            .toField("virtualMachineTemplate")
            .withTransformation(new IdToModelTransformer<>(virtualMachineTemplateModelService));
    }
}
