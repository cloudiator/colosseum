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

package cloud.sync.problems;

import cloud.resources.ImageInLocation;
import cloud.sync.Problem;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 05.05.15.
 */
public class ImageProblems {

    private ImageProblems() {

    }

    private abstract static class BaseImageProblem implements Problem {

        private final ImageInLocation imageInLocation;

        public BaseImageProblem(ImageInLocation imageInLocation) {
            checkNotNull(imageInLocation);
            this.imageInLocation = imageInLocation;
        }

        @Override public int getPriority() {
            return Priority.MEDIUM;
        }

        public ImageInLocation getImageInLocation() {
            return imageInLocation;
        }

        @Override public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof BaseImageProblem))
                return false;

            BaseImageProblem that = (BaseImageProblem) o;

            return imageInLocation.equals(that.imageInLocation);

        }

        @Override public int hashCode() {
            return imageInLocation.hashCode();
        }
    }


    public static class ImageNotInDatabase extends BaseImageProblem {

        public ImageNotInDatabase(ImageInLocation imageInLocation) {
            super(imageInLocation);
        }
    }


    public static class ImageMissesCredential extends BaseImageProblem {
        public ImageMissesCredential(ImageInLocation imageInLocation) {
            super(imageInLocation);
        }
    }


    public static class ImageMissesLocation extends BaseImageProblem {
        public ImageMissesLocation(ImageInLocation imageInLocation) {
            super(imageInLocation);
        }
    }


}
