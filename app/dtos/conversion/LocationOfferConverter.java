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
import dtos.LocationOfferDto;
import dtos.conversion.generic.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import models.GeoLocation;
import models.LocationOffer;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 14.04.15.
 */
public class LocationOfferConverter extends AbstractConverter<LocationOffer, LocationOfferDto> {

    private final ModelService<GeoLocation> geoLocationModelService;
    private final ModelService<LocationOffer> locationOfferModelService;

    @Inject protected LocationOfferConverter(ModelService<GeoLocation> geoLocationModelService,
        ModelService<LocationOffer> locationOfferModelService) {
        super(LocationOffer.class, LocationOfferDto.class);
        this.geoLocationModelService = geoLocationModelService;
        this.locationOfferModelService = locationOfferModelService;
    }

    @Override public void configure() {
        builder().from(Long.class, "parent").to(LocationOffer.class, "parent")
            .withTransformation(new IdToModelTransformer<>(locationOfferModelService));
        builder().from("locationScope").to("locationScope");
        builder().from("isAssignable").to("isAssignable");
        builder().from(Long.class, "geoLocation").to(GeoLocation.class, "geoLocation")
            .withTransformation(new IdToModelTransformer<>(geoLocationModelService));
    }
}
