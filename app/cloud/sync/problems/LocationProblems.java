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

import cloud.resources.LocationInCloud;
import cloud.sync.Problem;

/**
 * Created by daniel on 05.05.15.
 */
public class LocationProblems {

    private LocationProblems() {

    }

    private abstract static class BaseLocationProblem implements Problem {
        private final LocationInCloud locationInCloud;

        public BaseLocationProblem(LocationInCloud locationInCloud) {
            this.locationInCloud = locationInCloud;
        }

        @Override public int getPriority() {
            return Priority.HIGH;
        }

        public LocationInCloud getLocationInCloud() {
            return locationInCloud;
        }

        @Override public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof BaseLocationProblem))
                return false;

            BaseLocationProblem that = (BaseLocationProblem) o;

            return locationInCloud.equals(that.locationInCloud);

        }

        @Override public int hashCode() {
            return locationInCloud.hashCode();
        }
    }


    public static class LocationNotInDatabase extends BaseLocationProblem {
        public LocationNotInDatabase(LocationInCloud locationInCloud) {
            super(locationInCloud);
        }

        @Override public String toString() {
            return "LocationNotInDatabase";
        }
    }


    public static class LocationMissesCredential extends BaseLocationProblem {
        public LocationMissesCredential(LocationInCloud locationInCloud) {
            super(locationInCloud);
        }

        @Override public String toString() {
            return "LocationMissesCredential";
        }
    }


}
