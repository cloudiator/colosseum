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

package cloud.sync.detectors;

import com.google.inject.Inject;

import java.util.Optional;

import cloud.resources.LocationInCloud;
import cloud.sync.Problem;
import cloud.sync.ProblemDetector;
import cloud.sync.problems.LocationProblems;
import models.Location;
import models.service.LocationModelService;

/**
 * Created by daniel on 04.11.15.
 */
public class LocationNotInDatabaseDetector implements ProblemDetector<LocationInCloud> {

    private final LocationModelService locationModelService;

    @Inject public LocationNotInDatabaseDetector(LocationModelService locationModelService) {
        this.locationModelService = locationModelService;
    }

    @Override public Optional<Problem> apply(LocationInCloud locationInCloud) {
        Location location = locationModelService.getByRemoteId(locationInCloud.cloudId());
        if (location == null || !location.cloudCredentials()
            .contains(locationInCloud.credential())) {
            return Optional.of(new LocationProblems.LocationNotInDatabase(locationInCloud));
        }
        return Optional.empty();
    }
}
