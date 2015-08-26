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

package dtos.conversion.converters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dtos.CloudDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import models.Api;
import models.Cloud;
import models.service.ModelService;


@Singleton public class CloudConverter extends AbstractConverter<Cloud, CloudDto> {

    private final ModelService<Api> apiModelService;

    @Inject protected CloudConverter(ModelService<Api> apiModelService) {
        super(Cloud.class, CloudDto.class);
        this.apiModelService = apiModelService;
    }

    @Override public void configure() {
        binding().fromField("name").toField("name");
        binding().fromField("endpoint").toField("endpoint");
        binding(Long.class, Api.class).fromField("api").toField("api")
            .withTransformation(new IdToModelTransformer<>(apiModelService));
    }
}
