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

package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import components.job.JobService;
import controllers.internal.GenericApiController;
import api.dto.InstanceDto;
import api.binding.BindingService;
import models.Instance;
import models.Tenant;
import models.service.FrontendUserService;
import models.service.ModelService;

/**
 * Created by daniel on 09.04.15.
 */
public class InstanceController
    extends GenericApiController<Instance, InstanceDto, InstanceDto, InstanceDto> {

    private final JobService jobService;

    @Inject public InstanceController(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService, ModelService<Instance> modelService,
        TypeLiteral<Instance> typeLiteral, BindingService conversionService,
        JobService jobService) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
            conversionService);
        this.jobService = jobService;
    }

    @Override protected void postPost(Instance instance) {
        super.postPost(instance);
        this.jobService.newInstanceJob(instance, getActiveTenant());
    }
}
