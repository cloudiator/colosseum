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
import com.google.inject.TypeLiteral;
import dtos.ImageDto;
import dtos.conversion.transformers.IdToModelTransformer;
import dtos.conversion.transformers.ModelToListIdTransformer;
import models.*;
import models.service.ModelService;

import java.util.List;

/**
 * Created by daniel on 14.04.15.
 */
public class ImageConverter extends RemoteConverter<Image, ImageDto> {

    private final ModelService<Cloud> cloudModelService;
    private final ModelService<OperatingSystem> operatingSystemModelService;
    private final ModelService<Location> locationModelService;
    private final ModelService<CloudCredential> cloudCredentialModelService;

    @Inject protected ImageConverter(ModelService<Cloud> cloudModelService,
        ModelService<OperatingSystem> operatingSystemModelService,
        ModelService<Location> locationModelService,
        ModelService<CloudCredential> cloudCredentialModelService) {
        super(Image.class, ImageDto.class);
        this.cloudModelService = cloudModelService;
        this.operatingSystemModelService = operatingSystemModelService;
        this.locationModelService = locationModelService;
        this.cloudCredentialModelService = cloudCredentialModelService;
    }

    @Override public void configure() {

        super.configure();

        binding().fromField("name").toField("name");
        binding(Long.class, Cloud.class).fromField("cloud").toField("cloud")
            .withTransformation(new IdToModelTransformer<>(cloudModelService));
        binding(Long.class, OperatingSystem.class).fromField("operatingSystem")
            .toField("operatingSystem")
            .withTransformation(new IdToModelTransformer<>(operatingSystemModelService));
        binding(new TypeLiteral<List<Long>>() {
        }, new TypeLiteral<List<Location>>() {
        }).fromField("locations").toField("locations").withTransformation(
            new ModelToListIdTransformer<>(new IdToModelTransformer<>(locationModelService)));
        binding(new TypeLiteral<List<Long>>() {
        }, new TypeLiteral<List<CloudCredential>>() {
        }).fromField("cloudCredentials").toField("cloudCredentials").withTransformation(
            new ModelToListIdTransformer<>(
                new IdToModelTransformer<>(cloudCredentialModelService)));
        binding().fromField("defaultUsername").toField("defaultUsername");
    }
}
