package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.impl.AbstractValidatableDto;
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
        super();
    }

    public CloudHardwareDto(Long cloud, Long hardware, String uuid) {
        this.cloud = cloud;
        this.hardware = hardware;
        this.uuid = uuid;
    }

    @Override
    public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();
        return errors;
    }
}
