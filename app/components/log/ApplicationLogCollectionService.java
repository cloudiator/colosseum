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

import com.google.inject.Inject;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import models.ApplicationInstance;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 16.06.16.
 */
public class ApplicationLogCollectionService implements LogCollectionService {

    private final Set<LogFile> logFiles;

    @Inject ApplicationLogCollectionService(Set<LogFile> logFiles) {
        checkNotNull(logFiles);
        this.logFiles = logFiles;
    }

    @Override public File collectFor(ApplicationInstance applicationInstance) {

        Set<LogCollector> logCollectors = new HashSet<>();
        logFiles.forEach(logFile -> logCollectors.addAll(logFile.collectors(applicationInstance)));

        //todo replace runtime exception
        return new CompositeZipLogCollector(logCollectors).get()
            .orElseThrow(() -> new RuntimeException("Could not collect logs."));

    }
}
