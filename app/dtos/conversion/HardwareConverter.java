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
import dtos.HardwareDto;
import dtos.conversion.generic.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import dtos.conversion.transformers.ModelToListIdTransformer;
import models.Cloud;
import models.Hardware;
import models.HardwareOffer;
import models.Location;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 14.04.15.
 */
public class HardwareConverter extends AbstractConverter<Hardware, HardwareDto> {

    private final ModelService<HardwareOffer> hardwareOfferModelService;
    private final ModelService<Cloud> cloudModelService;
    private final ModelService<Location> locationModelService;

    @Inject protected HardwareConverter(ModelService<HardwareOffer> hardwareOfferModelService,
        ModelService<Cloud> cloudModelService, ModelService<Location> locationModelService) {
        super(Hardware.class, HardwareDto.class);
        this.hardwareOfferModelService = hardwareOfferModelService;
        this.cloudModelService = cloudModelService;
        this.locationModelService = locationModelService;
    }

    @Override public void configure() {
        builder().from("cloudUuid").to("cloudUuid");
        builder().from(Long.class, "hardwareOffer").to(HardwareOffer.class, "hardwareOffer")
            .withTransformation(new IdToModelTransformer<>(hardwareOfferModelService));
        builder().from(Long.class, "cloud").to(Cloud.class, "cloud")
            .withTransformation(new IdToModelTransformer<>(cloudModelService));
        builder().from("locations").to("locations").withUnsafeTransformation(
            new ModelToListIdTransformer<>(new IdToModelTransformer<>(locationModelService)));
    }
}
