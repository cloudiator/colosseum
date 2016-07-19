/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
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

import dtos.KeyPairDto;
import dtos.conversion.transformers.IdToModelTransformer;
import models.Cloud;
import models.KeyPair;
import models.VirtualMachine;
import models.service.ModelService;

/**
 * Created by daniel on 19.05.15.
 */
public class KeyPairConverter extends RemoteConverter<KeyPair, KeyPairDto> {

    private final ModelService<Cloud> cloudModelService;
    private final ModelService<VirtualMachine> virtualMachineModelService;

    @Inject protected KeyPairConverter(ModelService<Cloud> cloudModelService,
        ModelService<VirtualMachine> virtualMachineModelService) {
        super(KeyPair.class, KeyPairDto.class);
        this.cloudModelService = cloudModelService;
        this.virtualMachineModelService = virtualMachineModelService;
    }

    @Override public void configure() {
        binding(Long.class, Cloud.class).fromField("cloud").toField("cloud")
            .withTransformation(new IdToModelTransformer<>(cloudModelService));
        binding(Long.class, VirtualMachine.class).fromField("virtualMachine")
            .toField("virtualMachine")
            .withTransformation(new IdToModelTransformer<>(virtualMachineModelService));
        binding().fromField("privateKey").toField("privateKey");
        binding().fromField("publicKey").toField("publicKey");
    }
}
