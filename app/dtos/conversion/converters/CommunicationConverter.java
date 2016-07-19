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

import dtos.CommunicationDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import models.Communication;
import models.PortProvided;
import models.PortRequired;
import models.service.ModelService;

/**
 * Created by daniel on 14.04.15.
 */
public class CommunicationConverter extends AbstractConverter<Communication, CommunicationDto> {

    private final ModelService<PortRequired> portRequiredModelService;
    private final ModelService<PortProvided> portProvidedModelService;

    @Inject protected CommunicationConverter(ModelService<PortRequired> portRequiredModelService,
        ModelService<PortProvided> portProvidedModelService) {
        super(Communication.class, CommunicationDto.class);
        this.portRequiredModelService = portRequiredModelService;
        this.portProvidedModelService = portProvidedModelService;
    }

    @Override public void configure() {
        binding(Long.class, PortRequired.class).fromField("requiredPort").toField("requiredPort")
            .withTransformation(new IdToModelTransformer<>(portRequiredModelService));
        binding(Long.class, PortProvided.class).fromField("providedPort").toField("providedPort")
            .withTransformation(new IdToModelTransformer<>(portProvidedModelService));
    }
}
