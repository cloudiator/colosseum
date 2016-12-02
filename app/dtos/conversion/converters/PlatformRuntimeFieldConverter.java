package dtos.conversion.converters;

import dtos.PlatformRuntimeDto;
import dtos.conversion.DefaultFieldConverter;
import models.PlatformRuntime;

/**
 * Created by Daniel Seybold on 28.11.2016.
 */
public class PlatformRuntimeFieldConverter extends DefaultFieldConverter<PlatformRuntime, PlatformRuntimeDto> {
    protected PlatformRuntimeFieldConverter() {
        super(PlatformRuntime.class, PlatformRuntimeDto.class);
    }
}
