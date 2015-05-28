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
import dtos.CloudCredentialDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import models.Cloud;
import models.CloudCredential;
import models.FrontendGroup;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 14.04.15.
 */
public class CloudCredentialConverter
    extends AbstractConverter<CloudCredential, CloudCredentialDto> {

    private final ModelService<Cloud> cloudModelService;
    private final ModelService<FrontendGroup> frontendGroupModelService;

    @Inject protected CloudCredentialConverter(ModelService<Cloud> cloudModelService,
        ModelService<FrontendGroup> frontendGroupModelService) {
        super(CloudCredential.class, CloudCredentialDto.class);
        this.cloudModelService = cloudModelService;
        this.frontendGroupModelService = frontendGroupModelService;
    }

    @Override public void configure() {
        builder().from("user").to("user");
        builder().from("secret").to("secret");
        builder().from(Long.class, "cloud").to(Cloud.class, "cloud")
            .withTransformation(new IdToModelTransformer<>(cloudModelService));
        builder().from(Long.class, "frontendGroup").to(FrontendGroup.class, "frontendGroup")
            .withTransformation(new IdToModelTransformer<>(frontendGroupModelService));
    }
}
