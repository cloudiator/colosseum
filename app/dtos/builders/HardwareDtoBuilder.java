package dtos.builders;

import dtos.HardwareDto;

public class HardwareDtoBuilder {
    private Integer numberOfCpu;
    private Long mbOfRam;
    private Long localDiskSpace;

    public HardwareDtoBuilder numberOfCpu(Integer numberOfCpu) {
        this.numberOfCpu = numberOfCpu;
        return this;
    }

    public HardwareDtoBuilder mbOfRam(Long mbOfRam) {
        this.mbOfRam = mbOfRam;
        return this;
    }

    public HardwareDtoBuilder localDiskSpace(Long localDiskSpace) {
        this.localDiskSpace = localDiskSpace;
        return this;
    }

    public HardwareDto build() {
        return new HardwareDto(numberOfCpu, mbOfRam, localDiskSpace);
    }
}