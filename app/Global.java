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

import com.google.inject.Guice;
import com.google.inject.Injector;

import java.text.ParseException;
import java.util.Locale;

import cloud.config.CloudModule;
import cloud.sync.config.SolutionModule;
import components.execution.ExecutionModule;
import components.execution.Init;
import components.job.config.JobModule;
import components.log.LogCollectorModule;
import components.model.config.ModelValidationModule;
import components.scalability.AggregationModule;
import components.scalability.ScalingEngineModule;
import dtos.conversion.ConverterModule;
import models.service.DatabaseServiceModule;
import models.service.JPAModule;
import play.Application;
import play.GlobalSettings;
import play.data.format.Formatters;
import play.db.jpa.JPA;


/**
 * The Global class.
 * <p>
 * Extends the play framework global settings class, for configuration of the
 * application.
 * <p>
 * Provides onStart and onStop hook.
 * Implements common formatters.
 * Enables dependency injection within controllers.
 *
 * @author Daniel Baur
 */
public class Global extends GlobalSettings {

    /**
     * The injector used for creating the controllers.
     */
    private Injector injector;

    /**
     * On start hook.
     */
    public void onStart(final Application app) {
        super.onStart(app);


        this.injector = Guice
            .createInjector(new JPAModule(), new ConverterModule(app.classloader()),
                new DatabaseServiceModule(), new CloudModule(), new SolutionModule(),
                new JobModule(), new ExecutionModule(), new ScalingEngineModule(),
                new AggregationModule(), new LogCollectorModule(), new ModelValidationModule());


        final InitialData initialData = this.injector.getInstance(InitialData.class);

        JPA.withTransaction(initialData::loadInitialData);

        // register formatters
        this.registerFormatters();
    }

    @Override public void onStop(Application application) {
        super.onStop(application);

        injector.getInstance(Init.class).shutdown();
    }

    /**
     * Overridden to allow dependency injection with Google Guice.
     *
     * @param aClass The controller class to create
     * @param <A>    The controller to create
     * @return An instance of the given class, with injected dependencies.
     * @throws Exception if creating the controller fails.
     */
    @Override public <A> A getControllerInstance(Class<A> aClass) throws Exception {
        return injector.getInstance(aClass);
    }

    /**
     * Registers commonly used formatters.
     */
    protected void registerFormatters() {

        /**
         * A formatter for the string class.
         *
         * If a string is left empty, this formatter makes it null.
         * It also automatically trims the string
         */
        Formatters.register(String.class, new Formatters.SimpleFormatter<String>() {

            @Override public String parse(final String text, final Locale locale)
                throws ParseException {
                String trim = text.trim();
                if (trim.isEmpty()) {
                    return null;
                }
                return trim;
            }

            @Override public String print(final String string, final Locale locale) {
                if (string == null) {
                    return "";
                }
                return string;
            }
        });
    }
}
