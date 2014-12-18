package dtos.convert.converters.impl;

import dtos.ApplicationDto;
import models.Application;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by daniel seybold on 16.12.2014.
 */
public class ApplicationConverter extends BaseConverter<Application, ApplicationDto> {

    /**
     * Sets the dto to the application model.
     *
     * @param application    the application model where the dto should be set.
     * @param applicationDto the dto to be set.
     * @return the merged application object.
     */
    protected Application setDto(Application application, ApplicationDto applicationDto) {
        application.setName(applicationDto.name);
        return application;
    }

    @Override
    public Application toModel(ApplicationDto dto) {
        checkNotNull(dto);
        return this.setDto(new Application(), dto);
    }


    @Override
    public Application toModel(ApplicationDto dto, Application model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public ApplicationDto toDto(Application model) {
        checkNotNull(model);
        return new ApplicationDto(model.getName());
    }
}
