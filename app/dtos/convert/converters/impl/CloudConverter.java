package dtos.convert.converters.impl;

import com.google.inject.Singleton;
import dtos.CloudDto;
import dtos.convert.converters.api.ModelDtoConverter;
import models.Cloud;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Converter implementation for the Cloud model and the CloudDto DTO.
 */
@Singleton
public class CloudConverter implements ModelDtoConverter<Cloud, CloudDto> {

    /**
     * Sets the dto to the cloud model.
     *
     * @param cloud    the cloud model where the dto should be set.
     * @param cloudDto the dto to be set.
     * @return the merged cloud object.
     */
    protected Cloud setDto(Cloud cloud, CloudDto cloudDto) {
        cloud.setName(cloudDto.name);
        return cloud;
    }

    @Override
    public Cloud toModel(CloudDto cloudDto) {
        checkNotNull(cloudDto);
        return setDto(new Cloud(), cloudDto);
    }

    @Override
    public Cloud toModel(CloudDto cloudDto, Cloud model) {
        checkNotNull(cloudDto);
        checkNotNull(model);
        return setDto(model, cloudDto);
    }

    @Override
    public CloudDto toDto(Cloud cloud) {
        checkNotNull(cloud);
        return new CloudDto(cloud.getName());
    }
}
