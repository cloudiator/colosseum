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

package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.ValidatableDto;
import dtos.validation.validators.ModelIdValidator;
import dtos.validation.validators.NotNullValidator;
import models.Application;
import models.Component;
import models.VirtualMachineTemplate;
import models.service.impl.generic.BaseModelService;

/**
 * Created by daniel seybold on 16.12.2014.
 */
public class ApplicationComponentDto extends ValidatableDto {

    protected Long application;
    protected Long component;
    protected Long virtualMachineTemplate;

    public ApplicationComponentDto() {
        super();
    }

    public ApplicationComponentDto(Long application, Long component, Long virtualMachineTemplate) {
        this.application = application;
        this.component = component;
        this.virtualMachineTemplate = virtualMachineTemplate;
    }

    @Override public void validation() {
        validator(Long.class).validate(this.application).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.applicationService.get()));
        validator(Long.class).validate(this.component).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.componentService.get()));
        validator(Long.class).validate(this.virtualMachineTemplate)
            .withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.virtualMachineTemplateService.get()));
    }

    public Long getApplication() {
        return application;
    }

    public void setApplication(Long application) {
        this.application = application;
    }

    public Long getComponent() {
        return component;
    }

    public void setComponent(Long component) {
        this.component = component;
    }

    public Long getVirtualMachineTemplate() {
        return virtualMachineTemplate;
    }

    public void setVirtualMachineTemplate(Long virtualMachineTemplate) {
        this.virtualMachineTemplate = virtualMachineTemplate;
    }

    public static class References {

        @Inject private static Provider<BaseModelService<Application>> applicationService;
        @Inject private static Provider<BaseModelService<Component>> componentService;
        @Inject private static Provider<BaseModelService<VirtualMachineTemplate>>
            virtualMachineTemplateService;

        private References() {
        }
    }
}
