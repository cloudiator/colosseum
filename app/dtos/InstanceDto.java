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
import models.ApplicationComponent;
import models.VirtualMachine;
import models.service.impl.generic.BaseModelService;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel seybold on 17.12.2014.
 */
public class InstanceDto extends ValidatableDto {

    protected Long applicationComponent;
    protected Long virtualMachine;

    protected InstanceDto() {
        super();
    }

    public InstanceDto(Long applicationComponent, Long virtualMachine) {
        this.applicationComponent = applicationComponent;
        this.virtualMachine = virtualMachine;
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

    @Override public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();

        //validate applicationComponent reference
        ApplicationComponent applicationComponent = null;
        if (this.applicationComponent == null) {
            errors.add(new ValidationError("applicationComponent",
                "The applicationComponent is required."));
        } else {
            applicationComponent =
                References.applicationComponentService.get().getById(this.applicationComponent);
            if (applicationComponent == null) {
                errors.add(new ValidationError("applicationComponent",
                    "The given applicationComponent is invalid."));
            }
        }

        //validate virtualMachine reference
        VirtualMachine virtualMachine = null;
        if (this.virtualMachine == null) {
            errors.add(new ValidationError("virtualMachine", "The virtualMachine is required."));
        } else {
            virtualMachine = References.virtualMachineService.get().getById(this.virtualMachine);
            if (virtualMachine == null) {
                errors.add(
                    new ValidationError("virtualMachine", "The given virtualMachine is invalid."));
            }
        }

        return errors;
    }


    public static class References {

        @Inject public static Provider<BaseModelService<ApplicationComponent>>
            applicationComponentService;

        @Inject public static Provider<BaseModelService<VirtualMachine>> virtualMachineService;
    }
}
