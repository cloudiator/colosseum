package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.impl.AbstractValidatableDto;
import models.Cloud;
import models.Hardware;
import models.service.api.CloudServiceInterface;
import models.service.api.HardwareServiceInterface;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class CloudHardwareDto extends AbstractValidatableDto {

    public static class References{

        @Inject
        public static Provider<CloudServiceInterface> cloudService;

        @Inject
        public static Provider<HardwareServiceInterface> hardwareService;


    }

    public Long cloud;

    public Long hardware;

    public String uuid;


    public CloudHardwareDto() {

    }

    public CloudHardwareDto(Long cloud, Long hardware, String uuid) {
        this.cloud = cloud;
        this.hardware = hardware;
        this.uuid = uuid;
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

        //validate hardware reference
        Hardware hardware = null;
        if (this.hardware == null) {
            errors.add(new ValidationError("hardware", "The hardware is required."));
        } else {
            hardware = References.hardwareService.get().getById(this.hardware);
            if (hardware == null) {
                errors.add(new ValidationError("hardware", "The given hardware is invalid."));
            }
        }


        return errors;
    }
}
