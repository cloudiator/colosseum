package dtos;

import dtos.generic.impl.ValidatableDto;
import play.data.validation.ValidationError;

import java.util.List;

/**
 * Created by daniel on 09.01.15.
 */
public class CommunicationChannelDto extends ValidatableDto {

    public CommunicationChannelDto() {
    }

    public CommunicationChannelDto(Long communication, Long provider, Long consumer) {
        this.communication = communication;
        this.provider = provider;
        this.consumer = consumer;
    }

    public Long communication;

    public Long provider;

    public Long consumer;

    @Override
    public List<ValidationError> validateNotNull() {
        return super.validateNotNull();
    }
}
