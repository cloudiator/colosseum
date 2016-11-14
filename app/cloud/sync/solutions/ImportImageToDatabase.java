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
import cloud.sync.problems.ImageProblems;
import com.google.inject.Inject;
import models.Cloud;
import models.Image;
import models.Location;
import models.OperatingSystem;
import models.service.ImageModelService;
import models.service.LocationModelService;
import models.service.OperatingSystemService;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 07.05.15.
 */
public class ImportImageToDatabase implements Solution {

    private final ImageModelService imageModelService;
    private final OperatingSystemService operatingSystemService;
    private final LocationModelService locationModelService;

    @Inject
    public ImportImageToDatabase(ImageModelService imageModelService,
                                 OperatingSystemService operatingSystemService, LocationModelService locationModelService) {
        this.imageModelService = imageModelService;
        this.operatingSystemService = operatingSystemService;
        this.locationModelService = locationModelService;
    }

    @Override
    public boolean isSolutionFor(Problem problem) {
        return problem instanceof ImageProblems.ImageNotInDatabase;
    }

    @Override
    public void applyTo(Problem problem) throws SolutionException {
        checkArgument(isSolutionFor(problem));

        ImageProblems.ImageNotInDatabase imageNotInDatabase =
                (ImageProblems.ImageNotInDatabase) problem;

        Image existingImage = imageModelService.getByRemoteId(
                SlashEncodedId.of(imageNotInDatabase.getResource().id()).cloudId());
        if (existingImage != null) {
            existingImage.addCloudCredential(imageNotInDatabase.getResource().credential());
            return;
        }

        Cloud cloud = imageNotInDatabase.getResource().cloud();
        if (cloud == null) {
            throw new SolutionException();
        }
        Location location = null;
        if (imageNotInDatabase.getResource().location().isPresent()) {
            location = locationModelService.getByRemoteId(
                    SlashEncodedId.of(imageNotInDatabase.getResource().location().get().id())
                            .cloudId());
            if (location == null) {
                throw new SolutionException(String
                        .format("Could not import image %s as location %s is not (yet) imported.",
                                imageNotInDatabase.getResource(),
                                imageNotInDatabase.getResource().location().get()));
            }
        }

        OperatingSystem operatingSystem =
                new OperatingSystem(imageNotInDatabase.getResource().operatingSystem());
        operatingSystemService.save(operatingSystem);

        //todo check this
        Image image = new Image(imageNotInDatabase.getResource().cloudId(),
                imageNotInDatabase.getResource().providerId(),
                imageNotInDatabase.getResource().swordId(), cloud, location,
                imageNotInDatabase.getResource().name(), operatingSystem, null, null);
        image.addCloudCredential(imageNotInDatabase.getResource().credential());
        imageModelService.save(image);
    }
}
