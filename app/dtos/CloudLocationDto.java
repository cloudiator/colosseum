package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.impl.AbstractValidatableDto;
import models.Cloud;
import models.Location;
import models.service.api.CloudServiceInterface;
import models.service.impl.LocationService;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class CloudLocationDto extends AbstractValidatableDto {

    public static class References {

        @Inject
        public static Provider<CloudServiceInterface> cloudService;

        @Inject
        public static Provider<LocationService> locationService;
    }

    public Long cloud;
    public Long location;
    public String cloudUuid;

    public CloudLocationDto() {

    }

    public CloudLocationDto(Long cloud, Long location, String cloudUuid) {
        this.cloud = cloud;
        this.location = location;
        this.cloudUuid = cloudUuid;
    }

    @Override
    public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();

        //validate cloud reference
        Cloud cloud = null;
        if (this.cloud == null) {
            errors.add(new ValidationError("cloud", "The cloud is required."));
        } else {
            cloud = References.cloudService.get().getById(this.cloud);
            if (cloud == null) {
                errors.add(new ValidationError("cloud", "The given cloud is invalid."));
            }
        }

        //validate location reference
        Location location = null;
        if (this.location == null) {
            errors.add(new ValidationError("location", "The location is required."));
        } else {
            location = References.locationService.get().getById(this.location);
            if (location == null) {
                errors.add(new ValidationError("location", "The given location is invalid."));
            }
        }

        return errors;
    }
}
