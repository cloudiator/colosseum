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

package cloud.sync.watchdogs;

import cloud.CloudService;
import cloud.resources.HardwareInLocation;
import cloud.sync.AbstractCloudServiceWatchdog;
import cloud.sync.Problem;
import cloud.sync.problems.HardwareProblems;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import components.execution.SimpleBlockingQueue;
import components.execution.Stable;
import models.CloudCredential;
import models.Hardware;
import models.Location;
import models.service.HardwareModelService;

import java.util.concurrent.TimeUnit;

/**
 * Created by daniel on 07.05.15.
 */
@Stable public class HardwareWatchdog extends AbstractCloudServiceWatchdog {

    private final HardwareModelService hardwareModelService;

    @Inject protected HardwareWatchdog(CloudService cloudService,
        @Named(value = "problemQueue") SimpleBlockingQueue<Problem> simpleBlockingQueue,
        HardwareModelService hardwareModelService) {
        super(cloudService, simpleBlockingQueue);
        this.hardwareModelService = hardwareModelService;
    }



    @Override protected void watch(CloudService cloudService) {
        for (HardwareInLocation hardwareInLocation : cloudService.getDiscoveryService()
            .listHardwareFlavors()) {

            Hardware modelHardware = hardwareModelService.getByRemoteId(hardwareInLocation.id());

            if (modelHardware == null) {
                report(new HardwareProblems.HardwareNotInDatabase(hardwareInLocation));
            } else {
                CloudCredential credentialToSearchFor = null;
                for (CloudCredential cloudCredential : modelHardware.getCloudCredentials()) {
                    if (cloudCredential.getUuid().equals(hardwareInLocation.credential())) {
                        credentialToSearchFor = cloudCredential;
                        break;
                    }
                }

                if (credentialToSearchFor == null) {
                    report(new HardwareProblems.HardwareMissesCredential(hardwareInLocation));
                }

                Location locationToSearchFor = null;
                for (Location location : modelHardware.getLocations()) {
                    if (location.getCloud().getUuid().equals(hardwareInLocation.cloud()) && location
                        .getRemoteId().equals(hardwareInLocation.location())) {
                        locationToSearchFor = location;
                        break;
                    }
                }

                if (locationToSearchFor == null) {
                    report(new HardwareProblems.HardwareMissesLocation(hardwareInLocation));
                }
            }
        }
    }

    @Override public String toString() {
        return "HardwareWatchdog";
    }
}
