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
import dtos.ImageDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import dtos.conversion.transformers.ModelToListIdTransformer;
import models.*;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 14.04.15.
 */
public class ImageConverter extends AbstractConverter<Image, ImageDto> {

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
        builder().from("name").to("name");
        builder().from("cloudUuid").to("cloudUuid");
        builder().from(Long.class, "cloud").to(Cloud.class, "cloud")
            .withTransformation(new IdToModelTransformer<>(cloudModelService));
        builder().from(Long.class, "operatingSystem").to(OperatingSystem.class, "operatingSystem")
            .withTransformation(new IdToModelTransformer<>(operatingSystemModelService));
        builder().from("locations").to("locations").withUnsafeTransformation(
            new ModelToListIdTransformer<>(new IdToModelTransformer<>(locationModelService)));
        builder().from("cloudCredentials").to("cloudCredentials").withUnsafeTransformation(
            new ModelToListIdTransformer<>(new IdToModelTransformer<>(cloudCredentialModelService)));
    }
}
