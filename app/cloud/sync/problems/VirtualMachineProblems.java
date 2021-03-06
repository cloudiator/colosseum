/*
 * Copyright (c) 2014-2016 University of Ulm
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

import cloud.resources.VirtualMachineInLocation;
import models.VirtualMachine;

/**
 * Created by daniel on 11.11.16.
 */
public class VirtualMachineProblems {

    private VirtualMachineProblems() {

    }

    public static class VirtualMachineNotInDatabase extends AbstractProblem<VirtualMachineInLocation> {

        public VirtualMachineNotInDatabase(VirtualMachineInLocation resource) {
            super(resource);
        }

        @Override
        public int getPriority() {
            return Priority.LOW;
        }
    }

    public static class VirtualMachineInErrorState extends AbstractProblem<VirtualMachine> {

        public VirtualMachineInErrorState(VirtualMachine resource) {
            super(resource);
        }

        @Override
        public int getPriority() {
            return Priority.HIGH;
        }
    }

    public static class VirtualMachineIsSpare extends AbstractProblem<VirtualMachine> {

        public VirtualMachineIsSpare(VirtualMachine resource) {
            super(resource);
        }

        @Override
        public int getPriority() {
            return Priority.LOW;
        }
    }

}
