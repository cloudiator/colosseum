package dtos.builders;

import dtos.VirtualMachineDto;

public class VirtualMachineDtoBuilder {
    private String name;
    private Long cloud;
    private Long cloudImage;
    private Long cloudHardware;
    private Long cloudLocation;

    public VirtualMachineDtoBuilder name(String name) {
        this.name = name;
        return this;
    }

    public VirtualMachineDtoBuilder cloud(Long cloud) {
        this.cloud = cloud;
        return this;
    }

    public VirtualMachineDtoBuilder cloudImage(Long cloudImage) {
        this.cloudImage = cloudImage;
        return this;
    }

    public VirtualMachineDtoBuilder cloudHardware(Long cloudHardware) {
        this.cloudHardware = cloudHardware;
        return this;
    }

    public VirtualMachineDtoBuilder cloudLocation(Long cloudLocation) {
        this.cloudLocation = cloudLocation;
        return this;
    }

    public VirtualMachineDto build() {
        return new VirtualMachineDto(name, cloud, cloudImage, cloudHardware, cloudLocation);
    }
}