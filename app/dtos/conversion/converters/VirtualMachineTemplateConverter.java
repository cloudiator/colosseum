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
import dtos.VirtualMachineTemplateDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import models.*;
import models.service.ModelService;

/**
 * Created by daniel on 15.04.15.
 */
public class VirtualMachineTemplateConverter
    extends AbstractConverter<VirtualMachineTemplate, VirtualMachineTemplateDto> {

    private final ModelService<Cloud> cloudModelService;
    private final ModelService<Image> imageModelService;
    private final ModelService<Location> locationModelService;
    private final ModelService<Hardware> hardwareModelService;

    @Inject protected VirtualMachineTemplateConverter(ModelService<Cloud> cloudModelService,
        ModelService<Image> imageModelService, ModelService<Location> locationModelService,
        ModelService<Hardware> hardwareModelService) {
        super(VirtualMachineTemplate.class, VirtualMachineTemplateDto.class);
        this.cloudModelService = cloudModelService;
        this.imageModelService = imageModelService;
        this.locationModelService = locationModelService;
        this.hardwareModelService = hardwareModelService;
    }

    @Override public void configure() {
        builder().from(Long.class, "cloud").to(Cloud.class, "cloud")
            .withTransformation(new IdToModelTransformer<>(cloudModelService));
        builder().from(Long.class, "image").to(Image.class, "image")
            .withTransformation(new IdToModelTransformer<>(imageModelService));
        builder().from(Long.class, "hardware").to(Hardware.class, "hardware")
            .withTransformation(new IdToModelTransformer<>(hardwareModelService));
        builder().from(Long.class, "location").to(Location.class, "location")
            .withTransformation(new IdToModelTransformer<>(locationModelService));
    }
}
