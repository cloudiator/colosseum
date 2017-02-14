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

import dtos.VirtualMachineDto;
import dtos.conversion.transformers.IdToModelTransformer;
import de.uniulm.omi.cloudiator.persistance.entities.Cloud;
import de.uniulm.omi.cloudiator.persistance.entities.Hardware;
import de.uniulm.omi.cloudiator.persistance.entities.Image;
import de.uniulm.omi.cloudiator.persistance.entities.Location;
import de.uniulm.omi.cloudiator.persistance.entities.TemplateOptions;
import de.uniulm.omi.cloudiator.persistance.entities.VirtualMachine;
import de.uniulm.omi.cloudiator.persistance.repositories.ModelService;

/**
 * Created by daniel on 15.04.15.
 */
public class VirtualMachineConverter extends RemoteConverter<VirtualMachine, VirtualMachineDto> {

    private final ModelService<Cloud> cloudModelService;
    private final ModelService<Image> imageModelService;
    private final ModelService<Location> locationModelService;
    private final ModelService<Hardware> hardwareModelService;
    private final ModelService<TemplateOptions> templateOptionsModelService;

    @Inject protected VirtualMachineConverter(ModelService<Cloud> cloudModelService,
        ModelService<Image> imageModelService, ModelService<Location> locationModelService,
        ModelService<Hardware> hardwareModelService,
        ModelService<TemplateOptions> templateOptionsModelService) {
        super(VirtualMachine.class, VirtualMachineDto.class);
        this.cloudModelService = cloudModelService;
        this.imageModelService = imageModelService;
        this.locationModelService = locationModelService;
        this.hardwareModelService = hardwareModelService;
        this.templateOptionsModelService = templateOptionsModelService;
    }

    @Override public void configure() {

        super.configure();

        binding().fromField("name").toField("name");
        binding(Long.class, Cloud.class).fromField("cloud").toField("cloud")
            .withTransformation(new IdToModelTransformer<>(cloudModelService));
        binding(Long.class, Image.class).fromField("image").toField("image")
            .withTransformation(new IdToModelTransformer<>(imageModelService));
        binding(Long.class, Hardware.class).fromField("hardware").toField("hardware")
            .withTransformation(new IdToModelTransformer<>(hardwareModelService));
        binding(Long.class, Location.class).fromField("location").toField("location")
            .withTransformation(new IdToModelTransformer<>(locationModelService));
        binding(Long.class, TemplateOptions.class).fromField("templateOptions")
            .toField("templateOptions")
            .withTransformation(new IdToModelTransformer<>(templateOptionsModelService));
    }
}
