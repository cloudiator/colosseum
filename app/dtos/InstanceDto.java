package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.impl.AbstractValidatableDto;
import models.ApplicationComponent;
import models.VirtualMachine;
import models.service.api.ApplicationComponentServiceInterface;
import models.service.api.VirtualMachineServiceInterface;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel seybold on 17.12.2014.
 */
public class InstanceDto extends AbstractValidatableDto {

    public static class References{

        @Inject
        public static Provider<ApplicationComponentServiceInterface> applicationComponentService;

        @Inject
        public static Provider<VirtualMachineServiceInterface> virtualMachineService;
    }

    public Long applicationComponent;

    public Long virtualMachine;

    public InstanceDto(){

    }

    public InstanceDto(Long applicationComponent, Long virtualMachine){
        this.applicationComponent = applicationComponent;
        this.virtualMachine = virtualMachine;
    }

    @Override
    public List<ValidationError> validateNotNull() {
        List<ValidationError> errors = new ArrayList<>();

        //validate applicationComponent reference
        ApplicationComponent applicationComponent = null;
        if (this.applicationComponent == null) {
            errors.add(new ValidationError("applicationComponent", "The applicationComponent is required."));
        } else {
            applicationComponent = References.applicationComponentService.get().getById(this.applicationComponent);
            if (applicationComponent == null) {
                errors.add(new ValidationError("applicationComponent", "The given applicationComponent is invalid."));
            }
        }

        //validate virtualMachine reference
        VirtualMachine virtualMachine = null;
        if (this.virtualMachine == null) {
            errors.add(new ValidationError("virtualMachine", "The virtualMachine is required."));
        } else {
            virtualMachine = References.virtualMachineService.get().getById(this.virtualMachine);
            if (virtualMachine == null) {
                errors.add(new ValidationError("virtualMachine", "The given virtualMachine is invalid."));
            }
        }

        return errors;
    }
}
