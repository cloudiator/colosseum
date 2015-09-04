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
import dtos.LocationDto;
import dtos.conversion.transformers.IdToModelTransformer;
import dtos.conversion.transformers.ModelToListIdTransformer;
import models.Cloud;
import models.CloudCredential;
import models.GeoLocation;
import models.Location;
import models.service.ModelService;

import java.util.List;

/**
 * Created by daniel on 14.04.15.
 */
public class LocationConverter extends RemoteConverter<Location, LocationDto> {

    private final ModelService<Location> locationModelService;
    private final ModelService<Cloud> cloudModelService;
    private final ModelService<GeoLocation> geoLocationModelService;
    private final ModelService<CloudCredential> cloudCredentialModelService;

    @Inject protected LocationConverter(ModelService<Location> locationModelService,
        ModelService<Cloud> cloudModelService, ModelService<GeoLocation> geoLocationModelService,
        ModelService<CloudCredential> cloudCredentialModelService) {
        super(Location.class, LocationDto.class);
        this.locationModelService = locationModelService;
        this.cloudModelService = cloudModelService;
        this.geoLocationModelService = geoLocationModelService;
        this.cloudCredentialModelService = cloudCredentialModelService;
    }

    @Override public void configure() {

        super.configure();

        binding(Long.class, Cloud.class).fromField("cloud").toField("cloud")
            .withTransformation(new IdToModelTransformer<>(cloudModelService));

        binding(Long.class, Location.class).fromField("parent").toField("parent")
            .withTransformation(new IdToModelTransformer<>(locationModelService));

        binding().fromField("locationScope").toField("locationScope");

        binding().fromField("isAssignable").toField("isAssignable");

        binding(Long.class, GeoLocation.class).fromField("geoLocation").toField("geoLocation")
            .withTransformation(new IdToModelTransformer<>(geoLocationModelService));

        binding(new TypeLiteral<List<Long>>() {
        }, new TypeLiteral<List<CloudCredential>>() {
        }).fromField("cloudCredentials").toField("cloudCredentials").withTransformation(
            new ModelToListIdTransformer<>(
                new IdToModelTransformer<>(cloudCredentialModelService)));
    }
}
