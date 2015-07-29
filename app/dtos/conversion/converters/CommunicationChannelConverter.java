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
import dtos.CommunicationChannelDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import models.Communication;
import models.CommunicationChannel;
import models.Instance;
import models.service.ModelService;

/**
 * Created by daniel on 14.04.15.
 */
public class CommunicationChannelConverter
    extends AbstractConverter<CommunicationChannel, CommunicationChannelDto> {

    private final ModelService<Communication> communicationModelService;
    private final ModelService<Instance> instanceModelService;


    @Inject
    protected CommunicationChannelConverter(ModelService<Communication> communicationModelService,
        ModelService<Instance> instanceModelService) {
        super(CommunicationChannel.class, CommunicationChannelDto.class);
        this.communicationModelService = communicationModelService;
        this.instanceModelService = instanceModelService;
    }

    @Override public void configure() {
        builder().from(Long.class, "communication").to(Communication.class, "communication")
            .withTransformation(new IdToModelTransformer<>(communicationModelService));
        builder().from(Long.class, "provider").to(Instance.class, "provider")
            .withTransformation(new IdToModelTransformer<>(instanceModelService));
        builder().from(Long.class, "consumer").to(Instance.class, "consumer")
            .withTransformation(new IdToModelTransformer<>(instanceModelService));

    }
}
