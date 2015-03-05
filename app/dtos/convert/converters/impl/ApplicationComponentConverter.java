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

package dtos.convert.converters.impl;

import com.google.inject.Inject;
import dtos.ApplicationComponentDto;
import models.Application;
import models.ApplicationComponent;
import models.Component;
import models.VirtualMachineTemplate;
import models.service.api.ApplicationService;
import models.service.api.ComponentService;
import models.service.api.VirtualMachineTemplateService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel seybold on 17.12.2014.
 */
public class ApplicationComponentConverter extends BaseConverter<ApplicationComponent, ApplicationComponentDto> {

    private final ApplicationService applicationService;
    private final ComponentService componentService;
    private final VirtualMachineTemplateService virtualMachineTemplateService;

    @Inject
    ApplicationComponentConverter(ApplicationService applicationService, ComponentService componentService, VirtualMachineTemplateService virtualMachineTemplateService) {

        checkNotNull(applicationService);
        checkNotNull(componentService);
        checkNotNull(virtualMachineTemplateService);

        this.applicationService = applicationService;
        this.componentService = componentService;
        this.virtualMachineTemplateService = virtualMachineTemplateService;

    }

    /**
     * Sets the dto to the applicationComponent model.
     *
     * @param applicationComponent    the applicationComponent model where the dto should be set.
     * @param applicationComponentDto the dto to be set.
     * @return the merged applicationComponent object.
     */
    protected ApplicationComponent setDto(ApplicationComponent applicationComponent, ApplicationComponentDto applicationComponentDto) {

        Application application = applicationService.getById(applicationComponentDto.application);
        checkNotNull(application, "Could not retrieve application for id: " + applicationComponentDto.application);
        applicationComponent.setApplication(application);

        Component component = componentService.getById(applicationComponentDto.component);
        checkNotNull(component, "Could not retrieve component for id: " + applicationComponentDto.component);
        applicationComponent.setComponent(component);

        VirtualMachineTemplate virtualMachineTemplate = virtualMachineTemplateService.getById(applicationComponentDto.virtualMachineTemplate);
        checkNotNull(virtualMachineTemplate, "Could not retrieve virtual machine template for id: " + applicationComponentDto.validateNotNull());
        applicationComponent.setVirtualMachineTemplate(virtualMachineTemplate);

        return applicationComponent;
    }


    @Override
    public ApplicationComponent toModel(ApplicationComponentDto dto) {
        checkNotNull(dto);
        return this.setDto(new ApplicationComponent(), dto);
    }

    @Override
    public ApplicationComponent toModel(ApplicationComponentDto dto, ApplicationComponent model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public ApplicationComponentDto toDto(ApplicationComponent model) {
        checkNotNull(model);
        return new ApplicationComponentDto(model.getApplication().getId(), model.getComponent().getId(), model.getVirtualMachineTemplate().getId());
    }
}
