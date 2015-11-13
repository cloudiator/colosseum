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

import cloud.sync.Problem;
import cloud.sync.Solution;
import cloud.sync.SolutionException;
import cloud.sync.problems.ImageProblems;
import com.google.inject.Inject;
import models.Cloud;
import models.Image;
import models.Location;
import models.OperatingSystem;
import models.service.LocationModelService;
import models.service.ModelService;
import models.service.OperatingSystemService;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 07.05.15.
 */
public class CreateImageInDatabase implements Solution {

    private final ModelService<Image> imageModelService;
    private final ModelService<Cloud> cloudModelService;
    private final OperatingSystemService operatingSystemService;
    private final LocationModelService locationModelService;

    @Inject public CreateImageInDatabase(ModelService<Image> imageModelService,
        ModelService<Cloud> cloudModelService, OperatingSystemService operatingSystemService,
        LocationModelService locationModelService) {
        this.imageModelService = imageModelService;
        this.cloudModelService = cloudModelService;
        this.operatingSystemService = operatingSystemService;
        this.locationModelService = locationModelService;
    }

    @Override public boolean isSolutionFor(Problem problem) {
        return problem instanceof ImageProblems.ImageNotInDatabase;
    }

    @Override public void applyTo(Problem problem) throws SolutionException {
        checkArgument(isSolutionFor(problem));

        ImageProblems.ImageNotInDatabase imageNotInDatabase =
            (ImageProblems.ImageNotInDatabase) problem;

        Cloud cloud = imageNotInDatabase.getImageInLocation().cloud();
        if (cloud == null) {
            throw new SolutionException();
        }
        //todo check this
        Location location = null;
        if (imageNotInDatabase.getImageInLocation().location().isPresent()) {
            location = locationModelService
                .getByRemoteId(imageNotInDatabase.getImageInLocation().location().get().id());
            if (location == null) {
                throw new SolutionException(String
                    .format("Could not import image %s as location %s is not (yet) imported.",
                        imageNotInDatabase.getImageInLocation(),
                        imageNotInDatabase.getImageInLocation().location().get()));
            }
        }

        OperatingSystem operatingSystem =
            operatingSystemService.findByImageName(imageNotInDatabase.getImageInLocation().name());

        //todo check this
        Image image = new Image(imageNotInDatabase.getImageInLocation().id(),
            imageNotInDatabase.getImageInLocation().cloudProviderId(), cloud, location,
            imageNotInDatabase.getImageInLocation().name(), operatingSystem, null, null);
        imageModelService.save(image);
    }
}
