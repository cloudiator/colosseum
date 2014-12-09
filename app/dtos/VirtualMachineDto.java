package dtos;

import dtos.generic.impl.NamedDto;
import play.data.validation.ValidationError;

import java.util.List;

/**
 * Created by bwpc on 09.12.2014.
 */
public class VirtualMachineDto extends NamedDto {

    public Long cloud;

    public Long cloudImage;

    public Long cloudHardware;

    public Long cloudLocation;

    public VirtualMachineDto() {
    }

    public VirtualMachineDto(String name, Long cloud, Long cloudImage, Long cloudHardware, Long cloudLocation) {
        super(name);
        this.cloud = cloud;
        this.cloudImage = cloudImage;
        this.cloudHardware = cloudHardware;
        this.cloudLocation = cloudLocation;
    }

    @Override
    public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = super.validateNotNull();
        return errors;
    }
}
