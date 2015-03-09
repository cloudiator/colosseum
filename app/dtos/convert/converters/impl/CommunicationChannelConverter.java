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

package dtos.convert.converters.impl;

import com.google.inject.Inject;
import dtos.CommunicationChannelDto;
import dtos.convert.impl.BaseConverter;
import models.Communication;
import models.CommunicationChannel;
import models.Instance;
import models.service.api.CommunicationService;
import models.service.api.InstanceService;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 09.01.15.
 */
public class CommunicationChannelConverter extends BaseConverter<CommunicationChannel, CommunicationChannelDto> {

    private final CommunicationService communicationService;
    private final InstanceService instanceService;

    @Inject
    public CommunicationChannelConverter(CommunicationService communicationService, InstanceService instanceService) {
        this.communicationService = communicationService;
        this.instanceService = instanceService;
    }

    @Override
    public CommunicationChannel toModel(CommunicationChannelDto dto, CommunicationChannel model) {
        final Communication communication = this.communicationService.getById(dto.getCommunication());
        checkState(communication != null, "Could not retrieve communication for id = " + dto.getCommunication());

        final Instance provider = this.instanceService.getById(dto.getProvider());
        checkState(provider != null, "Could not retrieve instance for id = " + dto.getProvider());

        final Instance consumer = this.instanceService.getById(dto.getConsumer());
        checkState(consumer != null, "Could not retrieve instance for id = " + dto.getConsumer());

        model.setCommunication(communication);
        model.setProvider(provider);
        model.setConsumer(consumer);

        return model;
    }

    @Override
    public CommunicationChannelDto toDto(CommunicationChannel model) {
        return new CommunicationChannelDto(model.getCommunication().getId(), model.getProvider().getId(), model.getConsumer().getId());
    }
}
