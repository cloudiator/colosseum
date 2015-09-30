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

import cloud.resources.HardwareInLocation;
import cloud.sync.Problem;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 05.05.15.
 */
public class HardwareProblems {

    private HardwareProblems() {

    }

    private abstract static class BaseHardwareProblem implements Problem {

        private final HardwareInLocation hardwareInLocation;

        public BaseHardwareProblem(HardwareInLocation hardwareInLocation) {
            checkNotNull(hardwareInLocation);
            this.hardwareInLocation = hardwareInLocation;
        }

        @Override public int getPriority() {
            return Priority.MEDIUM;
        }

        public HardwareInLocation getHardwareInLocation() {
            return hardwareInLocation;
        }

        @Override public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof BaseHardwareProblem))
                return false;

            BaseHardwareProblem that = (BaseHardwareProblem) o;

            return hardwareInLocation.equals(that.hardwareInLocation);

        }

        @Override public int hashCode() {
            return hardwareInLocation.hashCode();
        }
    }


    public static class HardwareNotInDatabase extends BaseHardwareProblem {

        public HardwareNotInDatabase(HardwareInLocation hardwareInLocation) {
            super(hardwareInLocation);
        }

        @Override public String toString() {
            return "HardwareNotInDatabase";
        }
    }


    public static class HardwareMissesCredential extends BaseHardwareProblem {

        public HardwareMissesCredential(HardwareInLocation hardwareInLocation) {
            super(hardwareInLocation);
        }

        @Override public String toString() {
            return "HardwareMissesCredential";
        }
    }
}
