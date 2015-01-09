package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.impl.ValidatableDto;
import models.Cloud;
import models.Image;
import models.service.api.CloudServiceInterface;
import models.service.api.ImageServiceInterface;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class CloudImageDto extends ValidatableDto {


    public static class References{

        @Inject
        public static Provider<CloudServiceInterface> cloudService;

        @Inject
        public static Provider<ImageServiceInterface> imageService;
    }

    public Long cloud;
    public Long image;
    public String cloudUuid;

    public CloudImageDto() {

    }

    public CloudImageDto(Long cloud, Long image, String cloudUuid) {
        this.cloud = cloud;
        this.image = image;
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

        //validate cloud reference
        Image image = null;
        if (this.image == null) {
            errors.add(new ValidationError("image", "The image is required."));
        } else {
            image = References.imageService.get().getById(this.image);
            if (image == null) {
                errors.add(new ValidationError("image", "The given image is invalid."));
            }
        }

        return errors;
    }
}
