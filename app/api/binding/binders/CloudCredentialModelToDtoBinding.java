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

package api.binding.binders;

import com.google.inject.Inject;
import api.dto.CloudCredentialDto;
import api.binding.AbstractModelToDtoBinding;
import api.binding.transformers.IdToModelTransformer;
import models.Cloud;
import models.CloudCredential;
import models.Tenant;
import models.service.ModelService;

/**
 * Created by daniel on 14.04.15.
 */
public class CloudCredentialModelToDtoBinding
    extends AbstractModelToDtoBinding<CloudCredential, CloudCredentialDto> {

    private final ModelService<Cloud> cloudModelService;
    private final ModelService<Tenant> frontendGroupModelService;

    @Inject protected CloudCredentialModelToDtoBinding(ModelService<Cloud> cloudModelService,
        ModelService<Tenant> frontendGroupModelService) {
        super(CloudCredential.class, CloudCredentialDto.class);
        this.cloudModelService = cloudModelService;
        this.frontendGroupModelService = frontendGroupModelService;
    }

    @Override public void configure() {
        binding().fromField("user").toField("user");
        binding().fromField("secret").toField("secret");
        binding(Long.class, Cloud.class).fromField("cloud").toField("cloud")
            .withTransformation(new IdToModelTransformer<>(cloudModelService));
        binding(Long.class, Tenant.class).fromField("tenant").toField("tenant")
            .withTransformation(new IdToModelTransformer<>(frontendGroupModelService));
    }
}
