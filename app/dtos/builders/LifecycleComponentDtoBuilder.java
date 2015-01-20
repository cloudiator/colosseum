/*
 * Copyright (c) 2015 University of Ulm
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

package dtos.builders;

import dtos.LifecycleComponentDto;

public class LifecycleComponentDtoBuilder {
    private String name;
    private String download;
    private String install;
    private String start;
    private String stop;

    public LifecycleComponentDtoBuilder name(String name) {
        this.name = name;
        return this;
    }

    public LifecycleComponentDtoBuilder download(String download) {
        this.download = download;
        return this;
    }

    public LifecycleComponentDtoBuilder install(String install) {
        this.install = install;
        return this;
    }

    public LifecycleComponentDtoBuilder start(String start) {
        this.start = start;
        return this;
    }

    public LifecycleComponentDtoBuilder stop(String stop) {
        this.stop = stop;
        return this;
    }

    public LifecycleComponentDto build() {
        return new LifecycleComponentDto(name, download, install, start, stop);
    }
}