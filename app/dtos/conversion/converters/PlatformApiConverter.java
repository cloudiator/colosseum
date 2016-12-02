package dtos.conversion.converters;

import com.google.inject.Singleton;
import dtos.PlatformApiDto;
import dtos.conversion.AbstractConverter;
import models.PlatformApi;

/**
 * Created by Daniel Seybold on 24.11.2016.
 */
@Singleton public class PlatformApiConverter extends AbstractConverter<PlatformApi, PlatformApiDto> {

    protected PlatformApiConverter(){
            super(PlatformApi.class, PlatformApiDto.class);
    }

    @Override
    public void configure() {
        binding().fromField("name").toField("name");
        binding().fromField("internalProviderName").toField("internalProviderName");

    }
}
