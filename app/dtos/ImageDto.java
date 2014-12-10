package dtos;

import com.google.inject.Inject;
import dtos.generic.impl.NamedDto;
import models.OperatingSystem;
import models.service.api.OperatingSystemServiceInterface;

import com.google.inject.Provider;
import play.data.validation.ValidationError;

import java.util.List;

public class ImageDto extends NamedDto {

    public static class References{

        @Inject
        public static Provider<OperatingSystemServiceInterface> operatingSystemService;

    }

    public Long operatingSystem;

    public ImageDto() {

    }

    public ImageDto(String name, Long operatingSystem) {
        super(name);
        this.operatingSystem = operatingSystem;
    }

    @Override
    public List<ValidationError> validateNotNull() {
        final List<ValidationError> errors = super.validateNotNull();

        //validate cloud reference
        OperatingSystem operatingSystem = null;
        if (this.operatingSystem == null) {
            errors.add(new ValidationError("operatingsystem", "The operatingsystem is required."));
        } else {
            operatingSystem = References.operatingSystemService.get().getById(this.operatingSystem);
            if (operatingSystem == null) {
                errors.add(new ValidationError("operatingsystem", "The given operatingsystem is invalid."));
            }
        }

        return errors;
    }
}
