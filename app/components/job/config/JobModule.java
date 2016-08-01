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

package components.job.config;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

import components.execution.SimpleBlockingQueue;
import components.job.BaseJobService;
import components.job.Job;
import components.job.JobDispatcher;
import components.job.JobQueue;
import components.job.JobService;

/**
 * Created by daniel on 12.05.15.
 */
public class JobModule extends AbstractModule {

    @Override protected void configure() {
        bind(new TypeLiteral<SimpleBlockingQueue<Job>>() {
        }).annotatedWith(Names.named("jobQueue")).to(JobQueue.class);
        bind(JobService.class).to(BaseJobService.class);
        Multibinder<Runnable> runnables = Multibinder.newSetBinder(binder(), Runnable.class);
        runnables.addBinding().to(JobDispatcher.class);
    }
}
