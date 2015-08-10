/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package components.scalability;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import components.execution.Loop;
import components.execution.SimpleBlockingQueue;
import components.execution.Stable;
import components.job.Job;
import components.job.JobQueue;

/**
 * Created by Frank on 30.07.2015.
 */
public class AggregationModule  extends AbstractModule {

    @Override protected void configure() {
        bind(new TypeLiteral<SimpleBlockingQueue<Aggregation>>() {
        }).annotatedWith(Names.named("aggregationQueue")).to(AggregationQueue.class);
        Multibinder<Runnable> runnables = Multibinder.newSetBinder(binder(), Runnable.class);
        runnables.addBinding().to(AggregationWorker.class);
        runnables.addBinding().to(AggregationRestartWorker.class);
    }
}
