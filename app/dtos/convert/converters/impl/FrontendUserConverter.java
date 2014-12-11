package dtos.convert.converters.impl;

import dtos.FrontendUserDto;
import dtos.builders.FrontendUserDtoBuilder;
import dtos.convert.converters.api.ModelDtoConverter;
import models.FrontendUser;

import static com.google.inject.internal.util.$Preconditions.checkNotNull;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class FrontendUserConverter implements ModelDtoConverter<FrontendUser, FrontendUserDto> {

    /**
     * Sets the dto to the frontendUser model.
     *
     * @param frontendUser    the frontendUser model where the dto should be set.
     * @param frontendUserDto the dto to be set.
     * @return the merged hardware object.
     */
    protected FrontendUser setDto(FrontendUser frontendUser, FrontendUserDto frontendUserDto){
        frontendUser.setFirstName(frontendUserDto.firstName);
        frontendUser.setLastName(frontendUserDto.lastName);
        frontendUser.setMail(frontendUserDto.mail);
        frontendUser.setPassword(frontendUserDto.password);

        return frontendUser;
    }

    @Override
    public FrontendUser toModel(FrontendUserDto dto) {
        checkNotNull(dto);
        return this.setDto(new FrontendUser(), dto);
    }

    @Override
    public FrontendUser toModel(FrontendUserDto dto, FrontendUser model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public FrontendUserDto toDto(FrontendUser model) {
        checkNotNull(model);
        return new FrontendUserDtoBuilder()
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .mail(model.getMail())
                .password(model.getPassword())
                .build();
    }
}
