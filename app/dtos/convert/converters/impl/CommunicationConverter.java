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
import dtos.CommunicationDto;
import dtos.convert.impl.BaseConverter;
import models.ApplicationComponent;
import models.Communication;
import models.service.api.ApplicationComponentService;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 09.01.15.
 */
public class CommunicationConverter extends BaseConverter<Communication, CommunicationDto> {

    private final ApplicationComponentService applicationComponentService;

    @Inject
    public CommunicationConverter(ApplicationComponentService applicationComponentService) {
        this.applicationComponentService = applicationComponentService;
    }

    @Override
    public Communication toModel(CommunicationDto dto, Communication model) {
        checkNotNull(dto);
        checkNotNull(model);
        final ApplicationComponent provider = this.applicationComponentService.getById(dto.getProvider());
        checkState(provider != null, "Could not find application component with id " + dto.getProvider());

        final ApplicationComponent consumer = this.applicationComponentService.getById(dto.getConsumer());
        checkState(provider != null, "Could not find application component with id " + dto.getConsumer());

        model.setConsumer(consumer);
        model.setProvider(provider);
        model.setPort(dto.getPort());

        return model;
    }

    @Override
    public CommunicationDto toDto(Communication model) {
        checkNotNull(model);
        return new CommunicationDto(model.getProvider().getId(), model.getConsumer().getId(), model.getPort());
    }
}
