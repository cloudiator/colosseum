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

package cloud.sync.config;

import cloud.sync.Problem;
import cloud.sync.ProblemQueue;
import cloud.sync.ProblemSolver;
import cloud.sync.Solution;
import cloud.sync.solutions.*;
import cloud.sync.watchdogs.HardwareWatchdog;
import cloud.sync.watchdogs.ImageWatchdog;
import cloud.sync.watchdogs.LocationWatchdog;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import components.execution.Schedulable;
import components.execution.SimpleBlockingQueue;

/**
 * Created by daniel on 05.05.15.
 */
public class SolutionModule extends AbstractModule {

    @Override protected void configure() {
        this.bindSolutions();

        bind(new TypeLiteral<SimpleBlockingQueue<Problem>>() {
        }).annotatedWith(Names.named("problemQueue")).to(ProblemQueue.class);

        Multibinder<Schedulable> schedulableMultibinder =
            Multibinder.newSetBinder(binder(), Schedulable.class);
        schedulableMultibinder.addBinding().to(HardwareWatchdog.class);
        schedulableMultibinder.addBinding().to(ImageWatchdog.class);
        schedulableMultibinder.addBinding().to(LocationWatchdog.class);

        Multibinder<Runnable> runnableMultibinder =
            Multibinder.newSetBinder(binder(), Runnable.class);
        runnableMultibinder.addBinding().to(ProblemSolver.class);

    }

    private void bindSolutions() {
        Multibinder<Solution> solutionBinder = Multibinder.newSetBinder(binder(), Solution.class);
        solutionBinder.addBinding().to(CreateLocationInDatabase.class);
        solutionBinder.addBinding().to(ConnectLocationToCredential.class);
        solutionBinder.addBinding().to(CreateHardwareInDatabase.class);
        solutionBinder.addBinding().to(ConnectHardwareToCredential.class);
        solutionBinder.addBinding().to(CreateImageInDatabase.class);
        solutionBinder.addBinding().to(ConnectImageToCredential.class);
    }


}
