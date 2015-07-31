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
import com.google.inject.name.Named;
import components.execution.Loop;
import components.execution.SimpleBlockingQueue;
import play.db.jpa.Transactional;

/**
 * Created by daniel on 05.05.15.
 */
public class Solver implements Runnable {

    private final SolutionDatabase solutionDatabase;
    private final SimpleBlockingQueue<Problem> problemQueue;

    @Inject public Solver(SolutionDatabase solutionDatabase,
        @Named(value = "problemQueue") SimpleBlockingQueue<Problem> problemQueue) {
        this.solutionDatabase = solutionDatabase;
        this.problemQueue = problemQueue;
    }

    @Transactional @Loop @Override public void run() {

        Problem problemToSolve = null;
        try {
            problemToSolve = this.problemQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (problemToSolve != null) {
            try {
                final Solution solution = this.solutionDatabase.getSolution(problemToSolve);
                solution.applyTo(problemToSolve);
            } catch (SolutionNotFoundException e) {
                throw new IllegalStateException(e);
            } catch (SolutionException e) {
                this.problemQueue.add(problemToSolve);
            }
        }
    }
}
