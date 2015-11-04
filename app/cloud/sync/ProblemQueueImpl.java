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

import com.google.inject.Singleton;
import components.execution.SimpleBlockingQueue;
import components.execution.SimpleFifoPriorityBlockingQueue;
import components.execution.UniqueBlockingQueue;

/**
 * Created by daniel on 08.05.15.
 */
@Singleton public class ProblemQueueImpl implements SimpleBlockingQueue<Problem> {

    private final SimpleBlockingQueue<Problem> problemsToSolve;

    public ProblemQueueImpl() {
        // using <> as expected leads to wrong inferred type and thus a compile error.
        this.problemsToSolve =
            new UniqueBlockingQueue<Problem>(new SimpleFifoPriorityBlockingQueue<Problem>());
    }

    @Override public void add(Problem t) {
        this.problemsToSolve.add(t);
    }

    @Override public Problem take() throws InterruptedException {
        return this.problemsToSolve.take();
    }
}
