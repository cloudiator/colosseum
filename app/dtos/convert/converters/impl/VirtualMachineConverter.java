package dtos.convert.converters.impl;

import com.google.inject.Inject;
import dtos.VirtualMachineDto;
import dtos.builders.VirtualMachineDtoBuilder;
import models.*;
import models.service.impl.CloudHardwareFlavorService;
import models.service.impl.CloudImageService;
import models.service.impl.CloudLocationService;
import models.service.impl.CloudService;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel seybold on 09.12.2014.
 */
public class VirtualMachineConverter extends BaseConverter<VirtualMachine, VirtualMachineDto> {

    private final CloudService cloudService;
    private final CloudLocationService cloudLocationService;
    private final CloudHardwareFlavorService cloudHardwareFlavorService;
    private final CloudImageService cloudImageService;

    @Inject
    public VirtualMachineConverter(CloudService cloudService, CloudLocationService cloudLocationService, CloudHardwareFlavorService cloudHardwareFlavorService, CloudImageService cloudImageService) {

        checkNotNull(cloudService);
        checkNotNull(cloudLocationService);
        checkNotNull(cloudHardwareFlavorService);
        checkNotNull(cloudImageService);

        this.cloudService = cloudService;
        this.cloudLocationService = cloudLocationService;
        this.cloudHardwareFlavorService = cloudHardwareFlavorService;
        this.cloudImageService = cloudImageService;
    }

    protected VirtualMachine setDto(VirtualMachine virtualMachine, VirtualMachineDto virtualMachineDto) {
        virtualMachine.setName(virtualMachineDto.name);

        Cloud cloud = cloudService.getById(virtualMachineDto.cloud);
        checkState(cloud != null, "Could not retrieve cloud for id: " + virtualMachineDto.cloud);
        virtualMachine.setCloud(cloud);

        CloudLocation cloudLocation = cloudLocationService.getById(virtualMachineDto.cloudLocation);
        checkState(cloudLocation != null, "Could not retrieve cloudLocation for id: " + virtualMachineDto.cloudLocation);
        virtualMachine.setCloudLocation(cloudLocation);

        CloudHardware cloudHardware = cloudHardwareFlavorService.getById(virtualMachineDto.cloudHardware);
        checkState(cloudHardware != null, "Could not retrieve cloudHardware for id: " + virtualMachineDto.cloudHardware);
        virtualMachine.setCloudHardware(cloudHardware);

        CloudImage cloudImage = cloudImageService.getById(virtualMachineDto.cloudImage);
        checkState(cloudImage != null, "Could not retrieve cloudImage for id: " + virtualMachineDto.cloudImage);
        virtualMachine.setCloudImage(cloudImage);

        return virtualMachine;

    }

    @Override
    public VirtualMachine toModel(VirtualMachineDto dto) {
        checkNotNull(dto);
        return this.setDto(new VirtualMachine(), dto);
    }

    @Override
    public VirtualMachine toModel(VirtualMachineDto dto, VirtualMachine model) {
        checkNotNull(dto);
        checkNotNull(model);
        return this.setDto(model, dto);
    }

    @Override
    public VirtualMachineDto toDto(VirtualMachine model) {
        checkNotNull(model);
        return new VirtualMachineDtoBuilder()
                .name(model.getName())
                .cloud(model.getCloud().getId())
                .cloudImage(model.getCloudImage().getId())
                .cloudHardware(model.getCloudHardware().getId())
                .cloudLocation(model.getCloudLocation().getId())
                .build();
    }

}
