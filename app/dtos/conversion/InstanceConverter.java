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

package dtos.conversion;

import com.google.inject.Inject;
import dtos.InstanceDto;
import dtos.conversion.generic.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import models.ApplicationComponent;
import models.ApplicationInstance;
import models.Instance;
import models.VirtualMachine;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 14.04.15.
 */
public class InstanceConverter extends AbstractConverter<Instance, InstanceDto> {

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
        builder().from(Long.class, "applicationComponent")
            .to(ApplicationComponent.class, "applicationComponent")
            .withTransformation(new IdToModelTransformer<>(applicationComponentModelService));
        builder().from(Long.class, "applicationInstance")
            .to(ApplicationInstance.class, "applicationInstance")
            .withTransformation(new IdToModelTransformer<>(applicationInstanceModelService));
        builder().from(Long.class, "virtualMachine").to(VirtualMachine.class, "virtualMachine")
            .withTransformation(new IdToModelTransformer<>(virtualMachineModelService));
    }
}
