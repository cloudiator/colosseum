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
import dtos.MonitorSubscriptionDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import models.Monitor;
import models.MonitorSubscription;
import models.service.ModelService;

/**
 * Created by Frank on 02.08.2015.
 */
@Singleton public class MonitorSubscriptionConverter
    extends AbstractConverter<MonitorSubscription, MonitorSubscriptionDto> {

    private final ModelService<Monitor> monitorModelService;

    @Inject protected MonitorSubscriptionConverter(ModelService<Monitor> monitorModelService) {
        super(MonitorSubscription.class, MonitorSubscriptionDto.class);
        this.monitorModelService = monitorModelService;
    }

    @Override public void configure() {
        builder().from("type").to("type");
        builder().from("filterType").to("filterType");
        builder().from(Long.class, "monitor").to(Monitor.class, "monitor")
            .withTransformation(new IdToModelTransformer<>(monitorModelService));
        builder().from("endpoint").to("endpoint");
        builder().from("filterValue").to("filterValue");
    }
}
