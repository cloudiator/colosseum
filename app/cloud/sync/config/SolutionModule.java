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

import cloud.resources.HardwareInLocation;
import cloud.resources.ImageInLocation;
import cloud.resources.LocationInCloud;
import cloud.resources.VirtualMachineInLocation;
import cloud.sync.*;
import cloud.sync.detectors.*;
import cloud.sync.solutions.*;
import cloud.sync.watchdogs.*;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import components.execution.Schedulable;
import components.execution.SimpleBlockingQueue;
import models.Instance;
import models.VirtualMachine;
import play.Configuration;
import play.Environment;
import util.ConfigurationConstants;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 05.05.15.
 */
public class SolutionModule extends AbstractModule {

    private final Environment environment;
    private final Configuration configuration;

    public SolutionModule(Environment environment, Configuration configuration) {
        checkNotNull(environment, "environment is null.");
        checkNotNull(configuration, "configuration is null");
        this.environment = environment;
        this.configuration = configuration;
    }

    @Override protected void configure() {
        this.bindSolutions();

        bind(new TypeLiteral<SimpleBlockingQueue<Problem>>() {
        }).annotatedWith(Names.named("problemQueue")).to(ProblemQueueImpl.class);

        Multibinder<Schedulable> schedulableMultibinder =
            Multibinder.newSetBinder(binder(), Schedulable.class);
        schedulableMultibinder.addBinding().to(HardwareCloudWatchdog.class);
        schedulableMultibinder.addBinding().to(ImageWatchdog.class);
        schedulableMultibinder.addBinding().to(LocationWatchdog.class);
        schedulableMultibinder.addBinding().to(VirtualMachineInLocationWatchdog.class);
        schedulableMultibinder.addBinding().to(VirtualMachineWatchdog.class);
        schedulableMultibinder.addBinding().to(InstanceWatchdog.class);

        Multibinder<ProblemDetector<HardwareInLocation>> hardwareProblemDetectorBinder = Multibinder
            .newSetBinder(binder(), new TypeLiteral<ProblemDetector<HardwareInLocation>>() {
            });
        hardwareProblemDetectorBinder.addBinding().to(HardwareNotInDatabaseDetector.class);

        Multibinder<ProblemDetector<ImageInLocation>> imageProblemDetectorBinder =
            Multibinder.newSetBinder(binder(), new TypeLiteral<ProblemDetector<ImageInLocation>>() {
            });
        imageProblemDetectorBinder.addBinding().to(ImageNotInDatabaseDetector.class);

        Multibinder<ProblemDetector<LocationInCloud>> locationProblemDetectorBinder =
            Multibinder.newSetBinder(binder(), new TypeLiteral<ProblemDetector<LocationInCloud>>() {
            });
        locationProblemDetectorBinder.addBinding().to(LocationNotInDatabaseDetector.class);

        Multibinder<ProblemDetector<VirtualMachineInLocation>>
            virtualMachineInLocationProblemDetectorBinder = Multibinder
            .newSetBinder(binder(), new TypeLiteral<ProblemDetector<VirtualMachineInLocation>>() {
            });
        if (configuration
            .getBoolean(ConfigurationConstants.SYNC_VIRTUAL_MACHINE_NOT_IN_DATABASE_DETECTOR,
                false)) {
            virtualMachineInLocationProblemDetectorBinder.addBinding()
                .to(VirtualMachineNotInDatabaseDetector.class);
        }

        Multibinder<ProblemDetector<VirtualMachine>> virtualMachineProblemDetectorBinder =
            Multibinder.newSetBinder(binder(), new TypeLiteral<ProblemDetector<VirtualMachine>>() {
            });
        if (configuration
            .getBoolean(ConfigurationConstants.SYNC_VIRTUAL_MACHINE_ERROR_DETECTOR, false) {
            virtualMachineProblemDetectorBinder.addBinding()
                .to(VirtualMachineInErrorStateDetector.class);
        }

        Multibinder<ProblemDetector<Instance>> instanceProblemDetectorBinder =
            Multibinder.newSetBinder(binder(), new TypeLiteral<ProblemDetector<Instance>>() {
            });
        if (configuration.getBoolean(ConfigurationConstants.SYNC_INSTANCE_ERROR_DETECTOR, false)) {
            instanceProblemDetectorBinder.addBinding().to(InstanceInErrorStateDetector.class);
        }

        Multibinder<Runnable> runnableMultibinder =
            Multibinder.newSetBinder(binder(), Runnable.class);
        runnableMultibinder.addBinding().to(ProblemSolver.class);

    }

    private void bindSolutions() {
        Multibinder<Solution> solutionBinder = Multibinder.newSetBinder(binder(), Solution.class);
        solutionBinder.addBinding().to(ImportLocationInDatabase.class);
        solutionBinder.addBinding().to(ImportHardwareToDatabase.class);
        solutionBinder.addBinding().to(ImportImageToDatabase.class);
        solutionBinder.addBinding().to(DeleteSpareVirtualMachine.class);
        solutionBinder.addBinding().to(RetryVirtualMachine.class);
        solutionBinder.addBinding().to(RetryInstance.class);
    }


}
