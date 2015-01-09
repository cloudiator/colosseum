package dtos;

import dtos.generic.impl.ValidatableDto;
import play.data.validation.ValidationError;

import java.util.List;

/**
 * Created by daniel on 09.01.15.
 */
public class CommunicationDto extends ValidatableDto {

    public Long provider;
    public Long consumer;

    public CommunicationDto(Long provider, Long consumer) {
        this.provider = provider;
        this.consumer = consumer;
    }

    public CommunicationDto() {
    }

    @Override
    public List<ValidationError> validateNotNull() {
        return super.validateNotNull();
    }
}
