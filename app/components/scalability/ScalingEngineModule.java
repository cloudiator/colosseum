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
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Named;

import javax.inject.Singleton;

import components.execution.SimpleBlockingQueue;
import components.scalability.aggregation.Aggregation;
import components.scalability.aggregation.AggregationFactory;
import models.Monitor;
import play.Configuration;
import play.Environment;

/**
 * Created by Frank on 20.07.2015.
 */
public class ScalingEngineModule extends AbstractModule {

    private final Configuration configuration;

    public ScalingEngineModule(@SuppressWarnings("UnusedParameters") Environment environment,
                               Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(FrontendCommunicator.class).to(FrontendCommunicatorImpl.class);
        bind(AggregationFactory.class);
        //bind(ScalingEngine.class).to(ScalingEngineImpl.class);
    }

    @Singleton @Provides final ScalingEngine provideScalingEngine(FrontendCommunicator fc,
        @Named("aggregationQueue") SimpleBlockingQueue<Aggregation<Monitor>> aggregationQueue,
        Injector injector, AggregationFactory aggregationFactory) {
        return new ScalingEngineImpl(fc, aggregationQueue,
            configuration.getInt("colosseum.scalability.visor.port"), aggregationFactory);
        // return injector.getInstance(ScalingEngine.class);
    }
}
