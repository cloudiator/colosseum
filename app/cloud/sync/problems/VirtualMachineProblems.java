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
import cloud.sync.Problem;
import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 11.11.16.
 */
public class VirtualMachineProblems {

    private VirtualMachineProblems() {

    }

    private abstract static class BaseVirtualMachineProblem implements Problem {

        private final VirtualMachineInLocation virtualMachine;

        public BaseVirtualMachineProblem(VirtualMachineInLocation virtualMachine) {
            checkNotNull(virtualMachine, "virtual machine is null.");
            this.virtualMachine = virtualMachine;
        }

        @Override public int getPriority() {
            return Priority.MEDIUM;
        }

        public VirtualMachineInLocation getVirtualMachine() {
            return virtualMachine;
        }

        @Override public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof BaseVirtualMachineProblem))
                return false;

            BaseVirtualMachineProblem that = (BaseVirtualMachineProblem) o;

            return virtualMachine.equals(that.virtualMachine);

        }

        @Override public int hashCode() {
            return virtualMachine.hashCode();
        }

    }


    public static class VirtualMachineNotInDatabase extends BaseVirtualMachineProblem {

        public VirtualMachineNotInDatabase(VirtualMachineInLocation virtualMachine) {
            super(virtualMachine);
        }

        @Override public String toString() {
            return MoreObjects.toStringHelper(this).add("virtualMachine", getVirtualMachine())
                .toString();
        }
    }

}
