package dtos.convert.converters.impl;

import com.google.inject.Inject;
import dtos.ImageDto;
import dtos.convert.converters.api.ModelDtoConverter;
import models.Image;
import models.OperatingSystem;
import models.service.impl.OperatingSystemService;

import static com.google.inject.internal.util.$Preconditions.checkNotNull;
import static com.google.inject.internal.util.$Preconditions.checkState;

/**
 * Created by daniel seybold on 10.12.2014.
 */
public class ImageConverter extends BaseConverter<Image, ImageDto> {

    private final OperatingSystemService operatingSystemService;

    @Inject
    ImageConverter(OperatingSystemService operatingSystemService){

        checkNotNull(operatingSystemService);

        this.operatingSystemService = operatingSystemService;

    }

    protected Image setDto(Image image, ImageDto imageDto){
        image.setName(imageDto.name);

        OperatingSystem operatingSystem = operatingSystemService.getById(imageDto.operatingSystem);
        checkState( operatingSystem != null, "Could not retrieve operating system for id: " + imageDto.operatingSystem);
        image.setOperatingSystem(operatingSystem);

        return image;
    }

    @Override
    public Image toModel(ImageDto dto) {
        checkNotNull(dto);
        return this.setDto(new Image(), dto);
    }

    @Override
    public Image toModel(ImageDto dto, Image model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public ImageDto toDto(Image model) {
        checkNotNull(model);
        return new ImageDto(model.getName(), model.getOperatingSystem().getId());
    }
}
