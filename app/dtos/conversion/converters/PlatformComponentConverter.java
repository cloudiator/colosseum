package dtos.conversion.converters;

import com.google.inject.Singleton;

import dtos.PlatformComponentDto;
import dtos.conversion.DefaultFieldConverter;
import models.PlatformComponent;

/**
 * Created by Frank on 05.12.2016.
 */
@Singleton
public class PlatformComponentConverter
    extends DefaultFieldConverter<PlatformComponent, PlatformComponentDto>{

    protected PlatformComponentConverter() {
        super(PlatformComponent.class, PlatformComponentDto.class);
    }
}