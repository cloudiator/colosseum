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
import dtos.validation.ModelIdValidator;
import dtos.validation.NotNullValidator;
import models.ApplicationComponent;
import models.ApplicationInstance;
import models.VirtualMachine;
import models.service.impl.generic.BaseModelService;

/**
 * Created by daniel seybold on 17.12.2014.
 */
public class InstanceDto extends ValidatableDto {

    private Long applicationComponent;
    private Long applicationInstance;
    private Long virtualMachine;

    public InstanceDto() {
        super();
    }

    @Override public void validation() {
        validator(Long.class).validate(applicationComponent).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.applicationComponentService.get()));
        validator(Long.class).validate(applicationInstance).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.applicationInstanceService.get()));
        validator(Long.class).validate(virtualMachine).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.virtualMachineService.get()));
    }

    public Long getApplicationComponent() {
        return applicationComponent;
    }

    public void setApplicationComponent(Long applicationComponent) {
        this.applicationComponent = applicationComponent;
    }

    public Long getVirtualMachine() {
        return virtualMachine;
    }

    public void setVirtualMachine(Long virtualMachine) {
        this.virtualMachine = virtualMachine;
    }

    public Long getApplicationInstance() {
        return applicationInstance;
    }

    public void setApplicationInstance(Long applicationInstance) {
        this.applicationInstance = applicationInstance;
    }

    public static class References {

        @Inject private static Provider<BaseModelService<ApplicationComponent>>
            applicationComponentService;
        @Inject private static Provider<BaseModelService<VirtualMachine>> virtualMachineService;
        @Inject private static Provider<BaseModelService<ApplicationInstance>>
            applicationInstanceService;
    }
}
