package dtos.convert.converters.impl;

import dtos.ApiDto;
import dtos.convert.converters.api.ModelDtoConverter;
import models.Api;

import static com.google.inject.internal.util.$Preconditions.checkNotNull;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class ApiConverter extends BaseConverter<Api, ApiDto> {

    /**
     * Sets the dto to the cloud model.
     *
     * @param api    the cloud model where the dto should be set.
     * @param apiDto the dto to be set.
     * @return the merged cloud object.
     */
    protected Api setDto(Api api, ApiDto apiDto){
        api.setName(apiDto.name);
        return api;
    }

    @Override
    public Api toModel(ApiDto dto) {
        checkNotNull(dto);
        return this.setDto(new Api(), dto);
    }

    @Override
    public Api toModel(ApiDto dto, Api model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public ApiDto toDto(Api model) {
        checkNotNull(model);
        return new ApiDto(model.getName());
    }
}
