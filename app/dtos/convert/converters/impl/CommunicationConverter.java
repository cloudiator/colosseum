package dtos.convert.converters.impl;

import com.google.inject.Inject;
import dtos.CommunicationDto;
import models.ApplicationComponent;
import models.Communication;
import models.service.api.ApplicationComponentServiceInterface;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 09.01.15.
 */
public class CommunicationConverter extends BaseConverter<Communication, CommunicationDto> {

    private final ApplicationComponentServiceInterface applicationComponentService;

    @Inject
    public CommunicationConverter(ApplicationComponentServiceInterface applicationComponentService) {
        this.applicationComponentService = applicationComponentService;
    }

    protected Communication setDto(Communication communication, CommunicationDto communicationDto) {

        final ApplicationComponent provider = this.applicationComponentService.getById(communicationDto.provider);
        checkState(provider != null, "Could not find application component with id " + communicationDto.provider);

        final ApplicationComponent consumer = this.applicationComponentService.getById(communicationDto.consumer);
        checkState(provider != null, "Could not find application component with id " + communicationDto.consumer);

        communication.setConsumer(consumer);
        communication.setProvider(provider);

        return communication;
    }

    @Override
    public Communication toModel(CommunicationDto dto) {
        checkNotNull(dto);
        return this.setDto(new Communication(), dto);
    }

    @Override
    public Communication toModel(CommunicationDto dto, Communication model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public CommunicationDto toDto(Communication model) {
        checkNotNull(model);
        return new CommunicationDto(model.getProvider().getId(), model.getConsumer().getId());
    }
}
