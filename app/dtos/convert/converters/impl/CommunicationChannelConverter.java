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
import models.Communication;
import models.CommunicationChannel;
import models.Instance;
import models.service.api.CommunicationServiceInterface;
import models.service.api.InstanceServiceInterface;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 09.01.15.
 */
public class CommunicationChannelConverter extends BaseConverter<CommunicationChannel, CommunicationChannelDto> {

    private final CommunicationServiceInterface communicationService;
    private final InstanceServiceInterface instanceService;

    @Inject
    public CommunicationChannelConverter(CommunicationServiceInterface communicationService, InstanceServiceInterface instanceService) {
        this.communicationService = communicationService;
        this.instanceService = instanceService;
    }

    protected CommunicationChannel setDto(CommunicationChannel communicationChannel, CommunicationChannelDto communicationChannelDto) {

        final Communication communication = this.communicationService.getById(communicationChannelDto.communication);
        checkState(communication != null, "Could not retrieve communication for id = " + communicationChannelDto.communication);

        final Instance provider = this.instanceService.getById(communicationChannelDto.provider);
        checkState(provider != null, "Could not retrieve instance for id = " + communicationChannelDto.provider);

        final Instance consumer = this.instanceService.getById(communicationChannelDto.consumer);
        checkState(consumer != null, "Could not retrieve instance for id = " + communicationChannelDto.consumer);

        communicationChannel.setCommunication(communication);
        communicationChannel.setProvider(provider);
        communicationChannel.setConsumer(consumer);

        return communicationChannel;
    }

    @Override
    public CommunicationChannel toModel(CommunicationChannelDto dto) {
        checkNotNull(dto);
        return this.setDto(new CommunicationChannel(), dto);
    }

    @Override
    public CommunicationChannel toModel(CommunicationChannelDto dto, CommunicationChannel model) {
        checkNotNull(dto);
        checkNotNull(model);

        return this.setDto(model, dto);
    }

    @Override
    public CommunicationChannelDto toDto(CommunicationChannel model) {
        return new CommunicationChannelDto(model.getCommunication().getId(), model.getProvider().getId(), model.getConsumer().getId());
    }
}
