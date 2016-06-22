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

import java.io.File;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 06.06.16.
 */
public class LocalLogCollector implements LogCollector {

    private final File file;

    private LocalLogCollector(File file) {
        this.file = file;
    }

    public static LocalLogCollector of(File file) {
        checkNotNull(file);
        return new LocalLogCollector(file);
    }

    public static LocalLogCollector of(String location) {
        checkNotNull(location);
        checkArgument(!location.isEmpty());
        return new LocalLogCollector(new File(location));
    }

    public LocalLogCollector(String path) {
        this(new File(path));
    }

    @Override public Optional<File> get() {
        return Optional.of(file);
    }

    @Override public String name() {
        return file.getName();
    }

    @Override public String toString() {
        return MoreObjects.toStringHelper(this).add("file", file).toString();
    }
}
