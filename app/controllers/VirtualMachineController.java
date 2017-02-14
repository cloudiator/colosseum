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

package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import de.uniulm.omi.cloudiator.persistance.entities.Tenant;
import de.uniulm.omi.cloudiator.persistance.entities.VirtualMachine;
import de.uniulm.omi.cloudiator.persistance.repositories.FrontendUserService;
import de.uniulm.omi.cloudiator.persistance.repositories.ModelService;
import dtos.VirtualMachineDto;
import dtos.conversion.ModelDtoConversionService;

/**
 * Created by daniel on 09.04.15.
 */
public class VirtualMachineController extends
    GenericApiController<VirtualMachine, VirtualMachineDto, VirtualMachineDto, VirtualMachineDto> {

    private final ModelService<VirtualMachine> virtualMachineModelService;

    @Inject public VirtualMachineController(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService, ModelService<VirtualMachine> modelService,
        TypeLiteral<VirtualMachine> typeLiteral, ModelDtoConversionService conversionService) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
            conversionService);
        this.virtualMachineModelService = modelService;
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.VirtualMachineController.get(id).absoluteURL(request());
    }

    @Override protected void postPost(VirtualMachine virtualMachine) {
        virtualMachine.addCloudCredential(getCloudCredential(virtualMachine.cloud()));
        virtualMachine.bindOwner(getCloudCredential(virtualMachine.cloud()));
        this.virtualMachineModelService.save(virtualMachine);
    }

    @Override protected boolean preDelete(VirtualMachine entity) {
        return false;
    }
}
