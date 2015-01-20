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

import dtos.CloudApiDto;

public class CloudApiDtoBuilder {
    private Long api;
    private Long cloud;
    private String endpoint;

    public CloudApiDtoBuilder api(Long api) {
        this.api = api;
        return this;
    }

    public CloudApiDtoBuilder cloud(Long cloud) {
        this.cloud = cloud;
        return this;
    }

    public CloudApiDtoBuilder endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public CloudApiDto build() {
        return new CloudApiDto(api, cloud, endpoint);
    }
}