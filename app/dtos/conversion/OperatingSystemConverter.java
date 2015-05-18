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

package dtos.conversion;

import com.google.inject.Inject;
import dtos.OperatingSystemDto;
import dtos.conversion.generic.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import models.OperatingSystem;
import models.OperatingSystemVendor;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 15.04.15.
 */
public class OperatingSystemConverter
    extends AbstractConverter<OperatingSystem, OperatingSystemDto> {

    private final ModelService<OperatingSystemVendor> operatingSystemVendorModelService;

    @Inject protected OperatingSystemConverter(
        ModelService<OperatingSystemVendor> operatingSystemVendorModelService) {
        super(OperatingSystem.class, OperatingSystemDto.class);
        this.operatingSystemVendorModelService = operatingSystemVendorModelService;
    }

    @Override public void configure() {
        builder().from("operatingSystemArchitecture").to("operatingSystemArchitecture");
        builder().from(Long.class, "operatingSystemVendor")
            .to(OperatingSystemVendor.class, "operatingSystemVendor")
            .withTransformation(new IdToModelTransformer<>(operatingSystemVendorModelService));
        builder().from("version").to("version");
    }
}
