package dtos.convert.converters.impl;

import com.google.inject.Inject;
import dtos.CloudLocationDto;
import dtos.convert.converters.api.ModelDtoConverter;
import models.Cloud;
import models.CloudLocation;
import models.Location;
import models.service.impl.CloudService;
import models.service.impl.LocationService;

import static com.google.common.base.Preconditions.checkState;
import static com.google.inject.internal.util.$Preconditions.checkNotNull;

/**
 * Created by daniel seybold on 10.12.2014.
 */
public class CloudLocationConverter implements ModelDtoConverter<CloudLocation, CloudLocationDto> {

    private final CloudService cloudService;
    private final LocationService locationService;

    @Inject
    CloudLocationConverter(CloudService cloudService, LocationService locationService){

        checkNotNull(cloudService);
        checkNotNull(locationService);

        this.cloudService = cloudService;
        this.locationService = locationService;

    }

    protected CloudLocation setDto(CloudLocation cloudLocation, CloudLocationDto cloudLocationDto){
        cloudLocation.setUuid(cloudLocationDto.uuid);

        Cloud cloud = cloudService.getById(cloudLocationDto.cloud);
        checkState(cloud != null, "Could not retrieve cloud for id: " + cloudLocationDto.cloud);
        cloudLocation.setCloud(cloud);

        Location location = locationService.getById(cloudLocationDto.cloud);
        checkState(location != null, "Could not retrieve location for id: " + cloudLocationDto.location);
        cloudLocation.setLocation(location);

        return cloudLocation;
    }

    @Override
    public CloudLocation toModel(CloudLocationDto dto) {
        checkNotNull(dto);
        return this.setDto(new CloudLocation(), dto);
    }

    @Override
    public CloudLocation toModel(CloudLocationDto dto, CloudLocation model) {
        return null;
    }

    @Override
    public CloudLocationDto toDto(CloudLocation model) {
        return null;
    }
}
