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

import cloud.sword.resources.ImageInLocation;
import cloud.sync.Problem;
import cloud.sync.ProblemDetector;
import cloud.sync.problems.ImageProblems;
import models.Image;
import models.service.ImageModelService;

/**
 * Created by daniel on 04.11.15.
 */
public class ImageNotInDatabaseDetector implements ProblemDetector<ImageInLocation> {

    private final ImageModelService imageModelService;

    @Inject public ImageNotInDatabaseDetector(ImageModelService imageModelService) {
        this.imageModelService = imageModelService;
    }

    @Override public Optional<Problem> apply(ImageInLocation imageInLocation) {

        Image image = imageModelService.getByRemoteId(imageInLocation.cloudId());
        if (image == null || !image.cloudCredentials().contains(imageInLocation.credential())) {
            return Optional.of(new ImageProblems.ImageNotInDatabase(imageInLocation));
        }
        return Optional.empty();
    }
}
