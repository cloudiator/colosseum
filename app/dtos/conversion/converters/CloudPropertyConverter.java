/*
 * Copyright (c) 2014-2015 University of Ulm
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

package dtos.conversion.converters;

import com.google.inject.Inject;

import dtos.CloudPropertyDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import de.uniulm.omi.cloudiator.persistance.entities.Cloud;
import de.uniulm.omi.cloudiator.persistance.entities.CloudProperty;
import de.uniulm.omi.cloudiator.persistance.repositories.ModelService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 08.09.15.
 */
public class CloudPropertyConverter extends AbstractConverter<CloudProperty, CloudPropertyDto> {

    private final ModelService<Cloud> cloudModelService;

    @Inject public CloudPropertyConverter(ModelService<Cloud> cloudModelService) {
        super(CloudProperty.class, CloudPropertyDto.class);
        checkNotNull(cloudModelService);
        this.cloudModelService = cloudModelService;
    }

    @Override public void configure() {
        binding().fromField("key").toField("key");
        binding().fromField("value").toField("value");
        binding(Long.class, Cloud.class).fromField("cloud").toField("cloud")
            .withTransformation(new IdToModelTransformer<>(cloudModelService));
    }
}
