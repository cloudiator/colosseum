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

package cloud.sync;

import com.google.inject.Inject;

import java.util.Set;

/**
 * Created by daniel on 04.05.15.
 */
public class DefaultSolutionDatabase implements SolutionDatabase {



    private final Set<Solution> availableSolutions;

    @Inject public DefaultSolutionDatabase(Set<Solution> solutions) {
        this.availableSolutions = solutions;
    }

    @Override public Solution getSolution(Problem problem) throws SolutionNotFoundException {
        for (Solution solution : availableSolutions) {
            if (solution.isSolutionFor(problem))
                return solution;
        }
        throw new SolutionNotFoundException();
    }


}
