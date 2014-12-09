package dtos.builders;

import dtos.CloudHardwareDto;

public class CloudHardwareDtoBuilder {
    private Long cloud;
    private Long hardware;
    private String uuid;

    public CloudHardwareDtoBuilder cloud(Long cloud) {
        this.cloud = cloud;
        return this;
    }

    public CloudHardwareDtoBuilder hardware(Long hardware) {
        this.hardware = hardware;
        return this;
    }

    public CloudHardwareDtoBuilder uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public CloudHardwareDto createCloudHardwareDto() {
        return new CloudHardwareDto(cloud, hardware, uuid);
    }
}