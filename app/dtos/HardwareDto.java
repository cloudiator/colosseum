package dtos;

public class HardwareDto {

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
}
