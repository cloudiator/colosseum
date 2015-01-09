package dtos;

import dtos.generic.impl.ValidatableDto;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class HardwareDto extends ValidatableDto {

    public Integer numberOfCpu;
    public Long mbOfRam;
    public Long localDiskSpace;

    public HardwareDto() {

    }

    public HardwareDto(Integer numberOfCpu, Long mbOfRam, Long localDiskSpace) {
        this.numberOfCpu = numberOfCpu;
        this.mbOfRam = mbOfRam;
        this.localDiskSpace = localDiskSpace;
    }

    @Override
    public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();

        if(this.numberOfCpu <= 0){
            errors.add(new ValidationError("hardware", "NumberOfCpu must be greater 0"));
        }

        if(this.mbOfRam <= 0){
            errors.add(new ValidationError("hardware", "MbOfRam must be greater 0"));
        }

        if(this.mbOfRam <= 0){
            errors.add(new ValidationError("hardware", "LocalDiskSpace must be greater 0"));
        }

        return  errors;

    }
}
