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

package util.logging;

import play.Logger;

/**
 * Created by daniel on 05.11.15.
 */
public class Loggers {

    public static final String SYSTEM = "colosseum.system";
    public static final String CLOUD_SYNC = "colosseum.cloud.sync";
    public static final String MODEL = "colosseum.model";
    public static final String CLOUD_REMOTE = "colosseum.cloud.remote";
    public static final String EXECUTION = "colosseum.execution";
    public static final String DATABASE = "colosseum.database";
    public static final String API = "colosseum.api";
    public static final String INSTALLATION = "colosseum.cloud.installation";
    public static final String CLOUD_JOB = "colosseum.cloud.job";
    public static final String LOG_COLLECTION = "colosseum.logcollection";
    public static final String AUTH = "colosseum.auth";
    public static final String PLATFORM_JOB = "colosseum.platform.job";

    private Loggers() {
        throw new AssertionError("Intentionally left empty.");
    }

    public static Logger.ALogger of(String name) {
        return play.Logger.of(name);
    }

    public static Logger.ALogger of(Class<?> clazz) {
        return play.Logger.of(clazz);
    }



}
