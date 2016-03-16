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

package cloud.sync.solutions;

import cloud.SlashEncodedId;
import cloud.sync.Problem;
import cloud.sync.Solution;
import cloud.sync.SolutionException;
import cloud.sync.problems.LocationProblems;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import models.Cloud;
import models.Location;
import models.service.LocationModelService;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 04.05.15.
 */
public class ImportLocationInDatabase implements Solution {

    private final LocationModelService locationModelService;

    @Inject public ImportLocationInDatabase(LocationModelService locationModelService) {
        this.locationModelService = locationModelService;
    }

    @Override public boolean isSolutionFor(Problem problem) {
        return problem instanceof LocationProblems.LocationNotInDatabase;
    }

    @Override public void applyTo(Problem problem) throws SolutionException {
        checkArgument(isSolutionFor(problem));
        LocationProblems.LocationNotInDatabase locationNotInDatabase =
            (LocationProblems.LocationNotInDatabase) problem;

        Location existingLocation = locationModelService.getByRemoteId(
            SlashEncodedId.of(locationNotInDatabase.getLocationInCloud().id()).cloudId());
        if (existingLocation != null) {
            existingLocation
                .addCloudCredential(locationNotInDatabase.getLocationInCloud().credential());
            return;
        }

        Cloud cloud = locationNotInDatabase.getLocationInCloud().cloud();
        Optional<Location> parent;
        if (locationNotInDatabase.getLocationInCloud().parent().isPresent()) {
            parent = Optional.fromNullable(locationModelService.getByRemoteId(
                SlashEncodedId.of(locationNotInDatabase.getLocationInCloud().parent().get().id())
                    .cloudId()));
            if (!parent.isPresent()) {
                throw new SolutionException(String
                    .format("Could not import %s as parent %s was not found.",
                        locationNotInDatabase.getLocationInCloud(),
                        locationNotInDatabase.getLocationInCloud().parent().get()));
            }
        } else {
            parent = Optional.absent();
        }

        Location location = new Location(locationNotInDatabase.getLocationInCloud().cloudId(),
            locationNotInDatabase.getLocationInCloud().providerId(),
            locationNotInDatabase.getLocationInCloud().swordId(), cloud,
            locationNotInDatabase.getLocationInCloud().name(), null, parent.orNull(),
            locationNotInDatabase.getLocationInCloud().locationScope(),
            locationNotInDatabase.getLocationInCloud().isAssignable());

        this.locationModelService.save(location);
    }


}
