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

import dtos.CloudImageDto;

public class CloudImageDtoBuilder {
    private Long cloud;
    private Long image;
    private String cloudUuid;

    public CloudImageDtoBuilder cloud(Long cloud) {
        this.cloud = cloud;
        return this;
    }

    public CloudImageDtoBuilder image(Long image) {
        this.image = image;
        return this;
    }

    public CloudImageDtoBuilder cloudUuid(String cloudUuid) {
        this.cloudUuid = cloudUuid;
        return this;
    }

    public CloudImageDto build() {
        return new CloudImageDto(cloud, image, cloudUuid);
    }
}