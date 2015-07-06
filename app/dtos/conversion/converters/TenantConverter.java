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
import dtos.TenantDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import dtos.conversion.transformers.ModelToListIdTransformer;
import models.Tenant;
import models.service.api.FrontendUserService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 19.03.15.
 */
public class TenantConverter extends AbstractConverter<Tenant, TenantDto> {

    private final FrontendUserService frontendUserModelService;

    @Inject protected TenantConverter(FrontendUserService frontendUserModelService) {
        super(Tenant.class, TenantDto.class);
        checkNotNull(frontendUserModelService);
        this.frontendUserModelService = frontendUserModelService;
    }

    @Override public void configure() {
        builder().from("name").to("name");
        builder().from("frontendUsers").to("frontendUsers").withUnsafeTransformation(
            new ModelToListIdTransformer<>(new IdToModelTransformer<>(frontendUserModelService)));
    }
}
