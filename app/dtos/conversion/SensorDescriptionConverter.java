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
import com.google.inject.Singleton;
import dtos.CloudDto;
import dtos.ScheduleDto;
import dtos.SensorDescriptionDto;
import dtos.conversion.generic.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import models.Api;
import models.Cloud;
import models.Schedule;
import models.SensorDescription;
import models.service.api.generic.ModelService;


@Singleton public class SensorDescriptionConverter extends AbstractConverter<SensorDescription, SensorDescriptionDto> {

    @Inject protected SensorDescriptionConverter() {
        super(SensorDescription.class, SensorDescriptionDto.class);
    }

    @Override public void configure() {
        builder().from("className").to("className");
        builder().from("metricName").to("metricName");
        builder().from("isVmSensor").to("isVmSensor");
    }
}
