/*
 * Copyright (c) 2014-2016 University of Ulm
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

package components.log;

import java.io.File;
import java.util.Collections;
import java.util.Set;

import models.ApplicationInstance;

/**
 * Created by daniel on 17.06.16.
 */
public abstract class LocalLogFile implements LogFile {

    @Override public final Set<LogCollector> collectors(ApplicationInstance applicationInstance) {
        return Collections.singleton(LocalLogCollector.of(file()));
    }

    protected abstract File file();
}
