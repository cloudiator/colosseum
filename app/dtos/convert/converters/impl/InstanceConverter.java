package dtos.convert.converters.impl;

import com.google.inject.Inject;
import dtos.InstanceDto;
import dtos.convert.converters.api.ModelDtoConverter;
import models.ApplicationComponent;
import models.Instance;
import models.VirtualMachine;
import models.service.impl.ApplicationComponentService;
import models.service.impl.VirtualMachineService;

import static com.google.common.base.Preconditions.checkState;
import static com.google.inject.internal.util.$Preconditions.checkNotNull;

/**
 * Created by daniel seybold on 17.12.2014.
 */
public class InstanceConverter implements ModelDtoConverter<Instance, InstanceDto> {

    private final ApplicationComponentService applicationComponentService;
    private final VirtualMachineService virtualMachineService;

    @Inject
    InstanceConverter(ApplicationComponentService applicationComponentService, VirtualMachineService virtualMachineService){
        checkNotNull(applicationComponentService);
        checkNotNull(virtualMachineService);

        this.applicationComponentService = applicationComponentService;
        this.virtualMachineService = virtualMachineService;
    }

    /**
     * Sets the dto to the instance model.
     *
     * @param instance    the instance model where the dto should be set.
     * @param instanceDto the dto to be set.
     * @return the merged instance object.
     */
    protected Instance setDto(Instance instance, InstanceDto instanceDto){

        ApplicationComponent applicationComponent = applicationComponentService.getById(instanceDto.applicationComponent);
        checkState(applicationComponent != null, "Could not retrieve applicationComponent for id: " + instanceDto.applicationComponent);
        instance.setApplicationComponent(applicationComponent);

        VirtualMachine virtualMachine = virtualMachineService.getById(instanceDto.virtualMachine);
        checkState(virtualMachine != null, "Could not retrieve virtualMachine for id: " + instanceDto.virtualMachine);
        instance.setVirtualMachine(virtualMachine);

        return  instance;
    }

    @Override
    public Instance toModel(InstanceDto dto) {
        checkNotNull(dto);
        return this.setDto(new Instance(), dto);
    }

    @Override
    public Instance toModel(InstanceDto dto, Instance model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public InstanceDto toDto(Instance model) {
        checkNotNull(model);
        return new InstanceDto(model.getApplicationComponent().getId(), model.getVirtualMachine().getId());
    }
}
