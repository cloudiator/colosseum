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
import dtos.InstanceDto;
import dtos.conversion.transformers.IdToModelTransformer;
import models.ApplicationComponent;
import models.ApplicationInstance;
import models.Instance;
import models.VirtualMachine;
import models.service.ModelService;

/**
 * Created by daniel on 14.04.15.
 */
public class InstanceConverter extends RemoteConverter<Instance, InstanceDto> {

    private final ModelService<VirtualMachine> virtualMachineModelService;
    private final ModelService<ApplicationComponent> applicationComponentModelService;
    private final ModelService<ApplicationInstance> applicationInstanceModelService;

    @Inject protected InstanceConverter(ModelService<VirtualMachine> virtualMachineModelService,
        ModelService<ApplicationComponent> applicationComponentModelService,
        ModelService<ApplicationInstance> applicationInstanceModelService) {
        super(Instance.class, InstanceDto.class);
        this.virtualMachineModelService = virtualMachineModelService;
        this.applicationComponentModelService = applicationComponentModelService;
        this.applicationInstanceModelService = applicationInstanceModelService;
    }

    @Override public void configure() {
        //todo fix inheritance hierachy
        //super.configure();

        // todo workaround for incorrect inheritance
        // those two are copied from parent converter
        binding().fromField("remoteId").toField("remoteId");
        binding().fromField("remoteState").toField("remoteState");

        binding(Long.class, ApplicationComponent.class).fromField("applicationComponent")
            .toField("applicationComponent")
            .withTransformation(new IdToModelTransformer<>(applicationComponentModelService));
        binding(Long.class, ApplicationInstance.class).fromField("applicationInstance")
            .toField("applicationInstance")
            .withTransformation(new IdToModelTransformer<>(applicationInstanceModelService));
        binding(Long.class, VirtualMachine.class).fromField("virtualMachine")
            .toField("virtualMachine")
            .withTransformation(new IdToModelTransformer<>(virtualMachineModelService));
    }
}
