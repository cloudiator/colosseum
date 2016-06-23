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

import com.google.common.base.MoreObjects;
import com.google.common.io.ByteStreams;
import play.Logger;
import util.logging.Loggers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by daniel on 06.06.16.
 */
public class CompositeZipLogCollector implements LogCollector {

    Logger.ALogger LOGGER = Loggers.of(Loggers.LOG_COLLECTION);

    private final Set<LogCollector> collectors;

    public CompositeZipLogCollector(Set<LogCollector> collectors) {
        this.collectors = collectors;
    }

    @Override public Optional<File> get() {

        File zipFile;
        try {
            zipFile = File.createTempFile("cloudiator", ".zip");
            zipFile.deleteOnExit();
        } catch (IOException e) {
            LOGGER.warn("Could not create temporary zip file");
            return Optional.empty();
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)) {
            collectors.forEach(logCollector -> {
                final Optional<File> fileOptional = logCollector.get();
                if (fileOptional.isPresent()) {
                    try (FileInputStream fileInputStream = new FileInputStream(
                        fileOptional.get())) {
                        zipOutputStream.putNextEntry(new ZipEntry(logCollector.name()));
                        ByteStreams.copy(fileInputStream, zipOutputStream);
                    } catch (IOException e) {
                        LOGGER
                            .warn(String.format("Error while collecting log %s", logCollector), e);
                    }
                }
            });
        } catch (IOException e) {
            LOGGER.warn("Error while creating zip file", e);
        }
        return Optional.of(zipFile);
    }

    @Override public String name() {
        return toString();
    }

    @Override public String toString() {
        return MoreObjects.toStringHelper(this).add("collectors", collectors.toArray()).toString();
    }
}
