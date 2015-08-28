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
import cloud.resources.ImageInLocation;
import cloud.sync.AbstractCloudServiceWatchdog;
import cloud.sync.Problem;
import cloud.sync.problems.ImageProblems;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import components.execution.SimpleBlockingQueue;
import components.execution.Stable;
import models.CloudCredential;
import models.Location;
import models.service.ImageModelService;

import java.util.concurrent.TimeUnit;

/**
 * Created by daniel on 07.05.15.
 */
@Stable public class ImageWatchdog extends AbstractCloudServiceWatchdog {

    private final ImageModelService imageModelService;

    @Inject protected ImageWatchdog(CloudService cloudService,
        @Named(value = "problemQueue") SimpleBlockingQueue<Problem> simpleBlockingQueue,
        ImageModelService imageModelService) {
        super(cloudService, simpleBlockingQueue);
        this.imageModelService = imageModelService;
    }



    @Override protected void watch(CloudService cloudService) {
        for (ImageInLocation imageInLocation : cloudService.getDiscoveryService().listImages()) {

            models.Image modelImage = imageModelService.getByRemoteId(imageInLocation.id());

            if (modelImage == null) {
                report(new ImageProblems.ImageNotInDatabase(imageInLocation));
            } else {
                CloudCredential credentialToSearchFor = null;
                for (CloudCredential cloudCredential : modelImage.getCloudCredentials()) {
                    if (cloudCredential.getUuid().equals(imageInLocation.credential())) {
                        credentialToSearchFor = cloudCredential;
                        break;
                    }
                }

                if (credentialToSearchFor == null) {
                    report(new ImageProblems.ImageMissesCredential(imageInLocation));
                }

                Location locationToSearchFor = null;
                for (Location location : modelImage.getLocations()) {
                    if (location.getCloud().getUuid().equals(imageInLocation.cloud()) && location
                        .getRemoteId().equals(imageInLocation.location())) {
                        locationToSearchFor = location;
                        break;
                    }
                }

                if (locationToSearchFor == null) {
                    report(new ImageProblems.ImageMissesLocation(imageInLocation));
                }
            }



        }
    }

    @Override public long period() {
        return 1;
    }

    @Override public long delay() {
        return 0;
    }

    @Override public TimeUnit timeUnit() {
        return TimeUnit.MINUTES;
    }
}
