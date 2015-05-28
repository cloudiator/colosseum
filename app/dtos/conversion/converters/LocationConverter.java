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
import dtos.LocationDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import models.Cloud;
import models.GeoLocation;
import models.Location;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 14.04.15.
 */
public class LocationConverter extends AbstractConverter<Location, LocationDto> {

    private final ModelService<Location> locationModelService;
    private final ModelService<Cloud> cloudModelService;
    private final ModelService<GeoLocation> geoLocationModelService;

    @Inject protected LocationConverter(ModelService<Location> locationModelService,
        ModelService<Cloud> cloudModelService, ModelService<GeoLocation> geoLocationModelService) {
        super(Location.class, LocationDto.class);
        this.locationModelService = locationModelService;
        this.cloudModelService = cloudModelService;
        this.geoLocationModelService = geoLocationModelService;
    }

    @Override public void configure() {
        builder().from(Long.class, "cloud").to(Cloud.class, "cloud")
            .withTransformation(new IdToModelTransformer<>(cloudModelService));

        builder().from("cloudUuid").to("cloudUuid");

        builder().from(Long.class, "parent").to(Location.class, "parent")
            .withTransformation(new IdToModelTransformer<>(locationModelService));

        builder().from("locationScope").to("locationScope");

        builder().from("isAssignable").to("isAssignable");

        builder().from(Long.class, "geoLocation").to(GeoLocation.class, "geoLocation")
            .withTransformation(new IdToModelTransformer<>(geoLocationModelService));
    }
}
