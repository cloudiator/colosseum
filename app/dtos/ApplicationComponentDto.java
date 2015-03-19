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
import dtos.generic.impl.ValidatableDto;
import models.Application;
import models.Component;
import models.VirtualMachineTemplate;
import models.service.impl.generic.BaseModelService;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

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

    @Override public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();

        //validate application reference
        Application application;
        if (this.application == null) {
            errors.add(new ValidationError("application", "The application is required."));
        } else {
            application = References.applicationService.get().getById(this.application);
            if (application == null) {
                errors.add(new ValidationError("application", "The given application is invalid."));
            }
        }

        //validate component reference
        Component component = null;
        if (this.component == null) {
            errors.add(new ValidationError("component", "The component is required."));
        } else {
            component = References.componentService.get().getById(this.component);
            if (component == null) {
                errors.add(new ValidationError("component", "The given component is invalid."));
            }
        }

        //validate the virtual machine Template
        VirtualMachineTemplate virtualMachineTemplate;
        if (this.virtualMachineTemplate == null) {
            errors.add(new ValidationError("virtualMachineTemplate",
                "The virtual machine template is required."));
        } else {
            virtualMachineTemplate =
                References.virtualMachineTemplateService.get().getById(this.virtualMachineTemplate);
            if (virtualMachineTemplate == null) {
                errors.add(new ValidationError("virtualMachineTemplate",
                    "The virtual machine template is required."));
            }
        }

        return errors;
    }


    public static class References {

        @Inject public static Provider<BaseModelService<Application>> applicationService;

        @Inject public static Provider<BaseModelService<Component>> componentService;

        @Inject public static Provider<BaseModelService<VirtualMachineTemplate>>
            virtualMachineTemplateService;
    }
}
