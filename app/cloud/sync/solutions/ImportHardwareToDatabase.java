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
import cloud.sync.problems.HardwareProblems;
import com.google.inject.Inject;
import models.Cloud;
import models.Hardware;
import models.HardwareOffer;
import models.Location;
import models.service.HardwareModelService;
import models.service.LocationModelService;
import models.service.ModelService;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 07.05.15.
 */
public class ImportHardwareToDatabase implements Solution {

    private final HardwareModelService hardwareModelService;
    private final ModelService<HardwareOffer> hardwareOfferModelService;
    private final LocationModelService locationModelService;

    @Inject public ImportHardwareToDatabase(HardwareModelService hardwareModelService,
        ModelService<HardwareOffer> hardwareOfferModelService,
        LocationModelService locationModelService) {
        this.hardwareModelService = hardwareModelService;
        this.hardwareOfferModelService = hardwareOfferModelService;
        this.locationModelService = locationModelService;
    }

    @Override public boolean isSolutionFor(Problem problem) {
        return problem instanceof HardwareProblems.HardwareNotInDatabase;
    }

    @Override public void applyTo(Problem problem) throws SolutionException {
        checkArgument(isSolutionFor(problem));

        HardwareProblems.HardwareNotInDatabase hardwareNotInDatabase =
            (HardwareProblems.HardwareNotInDatabase) problem;

        Hardware existingHardware = hardwareModelService.getByRemoteId(
            SlashEncodedId.of(hardwareNotInDatabase.getHardwareInLocation().id()).cloudId());
        if (existingHardware != null) {
            existingHardware
                .addCloudCredential(hardwareNotInDatabase.getHardwareInLocation().credential());
            return;
        }

        Cloud cloud = hardwareNotInDatabase.getHardwareInLocation().cloud();
        Location location = null;
        if (hardwareNotInDatabase.getHardwareInLocation().location().isPresent()) {
            location = locationModelService.getByRemoteId(SlashEncodedId
                .of(hardwareNotInDatabase.getHardwareInLocation().location().get().id()).cloudId());
            if (location == null) {
                throw new SolutionException(String
                    .format("Could not import hardware %s as location %s is not (yet) imported.",
                        hardwareNotInDatabase.getHardwareInLocation(),
                        hardwareNotInDatabase.getHardwareInLocation().location().get()));
            }
        }

        //todo check this
        Hardware hardware = new Hardware(hardwareNotInDatabase.getHardwareInLocation().cloudId(),
            hardwareNotInDatabase.getHardwareInLocation().cloudProviderId(), cloud, location,
            hardwareNotInDatabase.getHardwareInLocation().name(),
            getHardwareOffer(hardwareNotInDatabase.getHardwareInLocation().numberOfCores(),
                hardwareNotInDatabase.getHardwareInLocation().mbRam(),
                hardwareNotInDatabase.getHardwareInLocation().gbDisk()));

        hardwareModelService.save(hardware);

    }

    private HardwareOffer getHardwareOffer(Integer numberOfCores, Long mbOfRam,
        @Nullable Float localDiskSpace) {

        for (HardwareOffer hardwareOffer : hardwareOfferModelService.getAll()) {
            if (hardwareOffer.getNumberOfCores().equals(numberOfCores)) {
                if (hardwareOffer.getMbOfRam().equals(mbOfRam)) {
                    //todo: check if this disk space comparison is ok, or if we need to consider disk space
                    if (localDiskSpace == null || localDiskSpace
                        .equals(hardwareOffer.getLocalDiskSpace())) {
                        return hardwareOffer;
                    }
                }
            }
        }

        HardwareOffer hardwareOffer = new HardwareOffer(numberOfCores, mbOfRam, localDiskSpace);
        this.hardwareOfferModelService.save(hardwareOffer);

        return hardwareOffer;
    }
}
