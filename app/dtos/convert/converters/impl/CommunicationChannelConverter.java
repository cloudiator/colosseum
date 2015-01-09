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
