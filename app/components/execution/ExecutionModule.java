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

package components.execution;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import com.google.inject.multibindings.Multibinder;

import play.Configuration;
import play.Environment;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;

/**
 * Created by daniel on 24.07.15.
 */
public class ExecutionModule extends AbstractModule {

    private final Configuration configuration;

    public ExecutionModule(@SuppressWarnings("UnusedParameters") Environment environment,
        Configuration configuration) {
        this.configuration = configuration;
    }

    @Override protected void configure() {

        bindInterceptor(Matchers.subclassesOf(Runnable.class), Matchers.annotatedWith(Loop.class),
            new LoopRunnableInterceptor());
        bindInterceptor(Matchers.subclassesOf(Runnable.class),
            Matchers.annotatedWith(Transactional.class),
            new TransactionalRunnableInterceptor(getProvider(JPAApi.class)));

        bind(ExecutionService.class).toInstance(new StableScheduledThreadExecutor(
            new ScheduledThreadPoolExecutorExecutionService(new LoggingScheduledThreadPoolExecutor(
                configuration.getInt("colosseum.execution.thread", 10)))));
        bind(ExecutionSystemInitialization.class).asEagerSingleton();

        Multibinder.newSetBinder(binder(), Runnable.class);
        Multibinder.newSetBinder(binder(), Schedulable.class);
    }
}
