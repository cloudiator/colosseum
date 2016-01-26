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
import com.google.inject.Singleton;
import api.dto.SensorDescriptionDto;
import api.binding.AbstractModelToDtoBinding;
import models.SensorDescription;


@Singleton public class SensorDescriptionModelToDtoBinding
    extends AbstractModelToDtoBinding<SensorDescription, SensorDescriptionDto> {

    @Inject protected SensorDescriptionModelToDtoBinding() {
        super(SensorDescription.class, SensorDescriptionDto.class);
    }

    @Override public void configure() {
        binding().fromField("className").toField("className");
        binding().fromField("metricName").toField("metricName");
        binding().fromField("isVmSensor").toField("isVmSensor");
    }
}
