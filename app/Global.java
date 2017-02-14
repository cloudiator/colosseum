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

import play.Application;
import play.GlobalSettings;
import play.data.format.Formatters;

import java.text.ParseException;
import java.util.Locale;


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
@Deprecated public class Global extends GlobalSettings {

    /**
     * On start hook.
     */
    public void onStart(final Application app) {
        super.onStart(app);
    }

    @Override public void onStop(Application application) {
        super.onStop(application);
    }
}
