package dtos.conversion.converters;

import dtos.PlatformHardwareDto;
import dtos.conversion.DefaultFieldConverter;
import models.PlatformHardware;

/**
 * Created by Daniel Seybold on 28.11.2016.
 */
public class PlatformHardwareFieldConverter extends DefaultFieldConverter<PlatformHardware, PlatformHardwareDto> {
    protected PlatformHardwareFieldConverter() {
        super(PlatformHardware.class, PlatformHardwareDto.class);
    }
}
