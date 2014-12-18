package dtos.convert.converters.impl;

import com.google.inject.Inject;
import dtos.CloudImageDto;
import dtos.builders.CloudImageDtoBuilder;
import models.Cloud;
import models.CloudImage;
import models.Image;
import models.service.impl.CloudService;
import models.service.impl.ImageService;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel seybold on 10.12.2014.
 */
public class CloudImageConverter extends BaseConverter<CloudImage, CloudImageDto> {

    private final CloudService cloudService;
    private final ImageService imageService;

    @Inject
    CloudImageConverter(CloudService cloudService, ImageService imageService) {

        checkNotNull(cloudService);
        checkNotNull(imageService);

        this.cloudService = cloudService;
        this.imageService = imageService;
    }

    protected CloudImage setDto(CloudImage cloudImage, CloudImageDto cloudImageDto) {
        cloudImage.setUuid(cloudImageDto.uuid);

        Cloud cloud = cloudService.getById(cloudImageDto.cloud);
        checkState(cloud != null, "Could not retrieve cloud for id: " + cloudImageDto.cloud);
        cloudImage.setCloud(cloud);

        Image image = imageService.getById(cloudImageDto.image);
        checkState(image != null, "Could not retrieve image for id: " + cloudImageDto.image);
        cloudImage.setImage(image);

        return cloudImage;

    }

    @Override
    public CloudImage toModel(CloudImageDto dto) {
        checkNotNull(dto);
        return this.setDto(new CloudImage(), dto);
    }

    @Override
    public CloudImage toModel(CloudImageDto dto, CloudImage model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public CloudImageDto toDto(CloudImage model) {
        checkNotNull(model);
        return new CloudImageDtoBuilder()
                .uuid(model.getUuid())
                .cloud(model.getCloud().getId())
                .image(model.getImage().getId())
                .build();

    }
}
